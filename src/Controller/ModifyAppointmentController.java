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
 * Controller for the modify appointment scene
 * @author Caleb Lugo
 */
public class ModifyAppointmentController implements Initializable{
    @FXML
    private TextField textFieldAppointmentId;
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
    
    private Appointment appointmentToModify = MainMenuController.getSelectedAppointment();
    
    /**
     * Initializes the modify appointment scene
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
        
        //Sets the data for appointment to be modified
        textFieldAppointmentId.setText(String.valueOf(appointmentToModify.getAppointmentId()));
        textFieldAppointmentTitle.setText(appointmentToModify.getTitle());
        textFieldAppointmentDescription.setText(appointmentToModify.getDescription());
        textFieldAppointmentLocation.setText(appointmentToModify.getLocation());
        textFieldAppointmentType.setText(appointmentToModify.getType());
        datePicker.setValue(appointmentToModify.getStart().toLocalDate());
        comboBoxStartHour.setValue(appointmentToModify.getStart().getHour());
        comboBoxStartMinute.setValue(appointmentToModify.getStart().getMinute());
        comboBoxEndHour.setValue(appointmentToModify.getEnd().getHour());
        comboBoxEndMinute.setValue(appointmentToModify.getEnd().getMinute());
        comboBoxAppointmentCustomer.setValue(getCustomerName(appointmentToModify.getCustomerId()));
        comboBoxAppointmentUser.setValue(getUserName(appointmentToModify.getUserId()));
        comboBoxAppointmentContact.setValue(getContactName(appointmentToModify.getContactId()));        
    }
    
    /**
     * Handles the save button
     * Error checks and modifies the object in the database
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
            
            int id = appointmentToModify.getAppointmentId();
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
            LocalDateTime lastUpdated = LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
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
                                        end.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime(), customerId, id)){
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
        
                String sql = "UPDATE client_schedule.appointments SET Title = "
                                      + "'" + title + "'," 
                                      + "Description =" + "'" + description + "',"
                                      + "Location =" +  "'" + location + "'," 
                                      + "Type =" +  "'" + type + "'," 
                                      + "Start =" +  "'" + utcZonedStart.toLocalDateTime() + "',"
                                      + "End =" +  "'" + utcZonedEnd.toLocalDateTime() + "'," 
                                      + "Last_Update =" +  "'" + lastUpdated + "'," 
                                      + "Last_Updated_By =" +  "'" + lastUpdatedBy + "'," 
                                      + "Customer_ID =" +  "'" + customerId + "'," 
                                      + "User_ID =" +  "'" + userId + "'," 
                                      + "Contact_ID =" +  "'" + contactId + "'"
                                      + "WHERE Appointment_ID = " + id;
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
     * Checks to see if a localdatetime object overlaps with another localdatetime object
     * within the same customer
     * @param date1 
     * @param date2 
     * @param customerId 
     * @param appointmentId 
     * @return isOverlapping
     */
    public boolean checkIsOverlapping(LocalDateTime date1, LocalDateTime date2, int customerId, int appointmentId){
        boolean isOverlapping = false;
        for(int i = 0; i < allAppointments.size(); i++){    
            if(!date2.isBefore(allAppointments.get(i).getStart()) && !date1.isAfter(allAppointments.get(i).getEnd())){
                if(allAppointments.get(i).getCustomerId() == customerId){
                    if(allAppointments.get(i).getAppointmentId() != appointmentId){
                        isOverlapping = true;
                    }
                }
            }
        }
        return isOverlapping;
    }
    
    /**
     * Returns to the main menu scene without saving changes
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
     * Returns the customer ID from the name
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
     * @return contactId
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
     * Returns the customer name from the ID
     * @param id
     * @return customerName
     */
    public String getCustomerName(int id){
        String customerName = "";
        for(int i = 0; i < allCustomers.size(); i++){
            if(allCustomers.get(i).getCustomerId() == id){
                customerName = allCustomers.get(i).getName();
            }
        }
        return customerName;
    }
    
    /**
     * Returns the user name from the ID
     * @param id
     * @return userName
     */
    public String getUserName(int id){
        String userName = "";
        for(int i = 0; i < allUsers.size(); i++){
            if(allUsers.get(i).getUserId() == id){
                userName = allUsers.get(i).getName();
            }
        }
        return userName;
    }
    
    /**
     * Returns the contact name from the ID
     * @param id
     * @return contactName
     */
    public String getContactName(int id){
        String contactName = "";
        for(int i = 0; i < allContacts.size(); i++){
            if(allContacts.get(i).getContactId() == id){
                contactName = allContacts.get(i).getName();
            }
        }
        return contactName;
    }
}
