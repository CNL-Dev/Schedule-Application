/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import Utility.DBQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for add appointment scene
 * @author Caleb Lugo
 */
public class AddAppointmentController implements Initializable{
    @FXML
    private TextField textFieldAppointmentTitle;
    @FXML
    private TextField textFieldAppointmentDescription;
    @FXML
    private TextField textFieldAppointmentLocation;
    @FXML
    private TextField textFieldAppointmentType;
    @FXML
    private ComboBox comboBoxAppointmentCustomer;
    @FXML
    private ComboBox comboBoxAppointmentUser;
    @FXML
    private ComboBox comboBoxAppointmentContact;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Integer> comboBoxStartHour;
    @FXML
    private ComboBox<Integer> comboBoxStartMinute;
    @FXML
    private ComboBox<Integer> comboBoxEndHour;
    @FXML
    private ComboBox<Integer> comboBoxEndMinute;
    
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<User> allUsers = FXCollections.observableArrayList();
    private ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private UserDAO userDAO = new UserDAO();
    private ContactDAO contactDAO = new ContactDAO();
    
    private ObservableList<Integer> hours = FXCollections.observableArrayList();
    private ObservableList<Integer> minutes = FXCollections.observableArrayList();
    
    /**
     * Initializes the add appointment scene
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Sets all data for combo boxes
        allAppointments.setAll(appointmentDAO.toLocal(appointmentDAO.getAll()));  
        
        allCustomers.setAll(customerDAO.getAll());
        ObservableList<String> customerName = FXCollections.observableArrayList();
        for(int i = 0; i < allCustomers.size(); i++){
            customerName.add(allCustomers.get(i).getName());
        }
        comboBoxAppointmentCustomer.setItems(customerName);
        
        allUsers.setAll(userDAO.getAll());
        ObservableList<String> userName = FXCollections.observableArrayList();
        for(int i = 0; i < allUsers.size(); i++){
            userName.add(allUsers.get(i).getName());
        }
        comboBoxAppointmentUser.setItems(userName);
        
        allContacts.setAll(contactDAO.getAll());
        ObservableList<String> contactName = FXCollections.observableArrayList(); 
        for(int i = 0; i < allContacts.size(); i++){
            contactName.add(allContacts.get(i).getName());
        }
        comboBoxAppointmentContact.setItems(contactName);
        
        //24 hours in a day
        for(int i = 0; i < 24; i++){
            hours.add(i);
        }     
        comboBoxStartHour.setItems(hours);
        comboBoxEndHour.setItems(hours);
        
        //4 15 minute intervals = 1 hour
        for(int i = 0; i <= 45; i += 15){
            minutes.add(i);
        }        
        comboBoxStartMinute.setItems(minutes);
        comboBoxEndMinute.setItems(minutes);
    }
    
    /**
     * Handles the save button
     * Error checks and saves the appointment to the database
     * @param event
     * @throws IOException 
     */
    public void handleButtonSave(ActionEvent event) throws IOException{
        try{
            //Office hours
            int second = 0;
            int nanoSecond = 0;
            LocalTime officeStart = LocalTime.of(8, 0, 0);
            LocalTime officeEnd = LocalTime.of(22, 0, 0);
            
            int id = generateUniqueId();            
            String title = textFieldAppointmentTitle.getText();
            String description = textFieldAppointmentDescription.getText();
            String location = textFieldAppointmentLocation.getText();
            String type = textFieldAppointmentType.getText();
            
            //Zoned to EST for office hours check
            ZonedDateTime start = datePicker.getValue().atTime(comboBoxStartHour.getValue(), comboBoxStartMinute.getValue(), second, nanoSecond)
                    .atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime end = datePicker.getValue().atTime(comboBoxEndHour.getValue(), comboBoxEndMinute.getValue(), second, nanoSecond)
                    .atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")); 
            
            //Zoned to UTC for database
            LocalDateTime createDate = LocalDateTime.now().atZone(ZoneOffset.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            String createdBy = "test";
            LocalDateTime lastUpdated = LocalDateTime.now().atZone(ZoneOffset.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            String lastUpdatedBy = "test";
            int customerId = getCustomerId();
            int userId = getUserId();
            int contactId = getContactId();
            
            //Input checks
            if(start.compareTo(end) > 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("End time cannot be earlier than start time.");
                alert.showAndWait();
            }else if(start.compareTo(end) == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Start and end time cannot be the same");
                alert.showAndWait();
            }else if(start.toLocalTime().isBefore(officeStart) || start.toLocalTime().isAfter(officeEnd)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Cannot start/end an appointment before office hours (8:00 - 22:00).");
                alert.showAndWait();
            }else if (end.toLocalTime().isBefore(officeStart) || end.toLocalTime().isAfter(officeEnd)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Cannot start/end an appointment after office hours (8:00 - 22:00).");
                alert.showAndWait();
            }else if(checkIsWeekend(start.toLocalDateTime()) == true || checkIsWeekend(end.toLocalDateTime()) == true){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Cannot start an appointment during the weekend.");
                alert.showAndWait();
            }else if(checkIsOverlapping(start.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime(), 
                                        end.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime(), customerId)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Customer cannot have appointments overlapping each other.");
                alert.showAndWait();
            }else{              
                //Zoned to UTC for database             
                ZonedDateTime utcZonedStart = datePicker.getValue().atTime(comboBoxStartHour.getValue(), comboBoxStartMinute.getValue(), second, nanoSecond)
                        .atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                ZonedDateTime utcZonedEnd = datePicker.getValue().atTime(comboBoxEndHour.getValue(), comboBoxEndMinute.getValue(), second, nanoSecond)
                        .atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                
                Statement statement = DBQuery.getStatement();
        
                String sql = "INSERT INTO client_schedule.appointments(Appointment_ID, Title, Description,"
                                      + " Location, Type, Start, End, Create_Date, Created_By, Last_Update,"
                                      + " Last_Updated_By, Customer_ID, User_ID, Contact_ID)"
                                      + "VALUES('" + id + "'," 
                                      + "'" + title + "'," 
                                      + "'" + description + "',"
                                      + "'" + location + "'," 
                                      + "'" + type + "'," 
                                      + "'" + utcZonedStart.toLocalDateTime() + "',"
                                      + "'" + utcZonedEnd.toLocalDateTime() + "'," 
                                      + "'" + createDate + "'," 
                                      + "'" + createdBy + "',"
                                      + "'" + lastUpdated + "'," 
                                      + "'" + lastUpdatedBy + "'," 
                                      + "'" + customerId + "'," 
                                      + "'" + userId + "'," 
                                      + "'" + contactId + "')";
                statement.execute(sql);
                ResultSet rs = statement.getResultSet();
                
                Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuScene.fxml"));
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illegal entry");
            alert.setContentText("Illegal entry in one of the fields, please look at the entries and try again.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    
    /**
     * Checks the date to see if it is a weekend
     * @param date
     * @return isWeekend
     */
    public boolean checkIsWeekend(LocalDateTime date){
        DayOfWeek day = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));
        boolean isWeekend = false;
        switch(day){
            case SATURDAY:
                isWeekend = true;
                break;
            case SUNDAY:
                isWeekend = true;
                break;
            default:
                break;
        }
        return isWeekend;
    }
    
    /**
     * Checks to see if two localdatetime objects overlaps with another localdatetime object
     * within the same customer
     * @param date1 
     * @param date2 
     * @param customerId 
     * @return isOverlapping
     */
    /**
     * Checks to see if a localdatetime object overlaps with another localdatetime object
     * within the same customer
     * @param date1 
     * @param date2 
     * @param customerId 
     * @return isOverlapping
     */
    public boolean checkIsOverlapping(LocalDateTime date1, LocalDateTime date2, int customerId){
        boolean isOverlapping = false;
        for(int i = 0; i < allAppointments.size(); i++){    
            if(!date2.isBefore(allAppointments.get(i).getStart()) && !date1.isAfter(allAppointments.get(i).getEnd())){
                if(allAppointments.get(i).getCustomerId() == customerId){
                    isOverlapping = true;
                }
            }
        }
        return isOverlapping;
    }
    
    /**
     * Handles the cancel button
     * Returns the main menu 
     * @param event
     * @throws IOException 
     */
    public void handleButtonCancel(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuScene.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();   
    }
    
    /**
     * Gets the customer ID from the name
     * @return customerId
     */
    public int getCustomerId(){
        int customerId = 0;
        String customer = (String)comboBoxAppointmentCustomer.getSelectionModel().getSelectedItem();
        for(int i = 0; i < allCustomers.size(); i++){
            if(customer == allCustomers.get(i).getName()){
                customerId = allCustomers.get(i).getCustomerId();
            }
        }
        return customerId;
    }
    
    /**
     * Returns the user ID from the name
     * @return userId
     */
    public int getUserId(){
        int userId = 0;
        String user = (String)comboBoxAppointmentUser.getSelectionModel().getSelectedItem();
        for(int i = 0; i < allUsers.size(); i++){
            if(user == allUsers.get(i).getName()){
                userId = allUsers.get(i).getUserId();
            }
        }
        return userId;
    }
    
    /**
     * Returns the contact ID from the name
     * @return 
     */
    public int getContactId(){
        int contactId = 0;
        String contact = (String)comboBoxAppointmentContact.getSelectionModel().getSelectedItem();
        for(int i = 0; i < allContacts.size(); i++){
            if(contact == allContacts.get(i).getName()){
                contactId = allContacts.get(i).getContactId();
            }
        }
        return contactId;
    }
    
    /**
     * Generates a unique ID sequential to the last ID used for appointments
     * @return biggestId
     */
    public int generateUniqueId(){
        int biggestId = 0;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        allAppointments.setAll(appointmentDAO.getAll());
        for(int i = 0; i < allAppointments.size(); i++){
            if(allAppointments.get(i).getAppointmentId() >= biggestId){
                biggestId = allAppointments.get(i).getAppointmentId();
            }
        }
        //Should be a unique ID
        return biggestId + 1;
    }    
}
