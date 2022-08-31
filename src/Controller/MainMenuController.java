/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Utility.DBQuery;
import Utility.JDBC;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for main menu scene
 * @author Caleb Lugo
 */
public class MainMenuController implements Initializable{
    
    @FXML
    private TableView tableViewCustomers;
    @FXML
    private TableView tableViewAppointments;
    @FXML
    private TableView tableViewContactSchedule;
    @FXML
    private TableColumn tableColumnCustomerId;
    @FXML
    private TableColumn tableColumnCustomerName;
    @FXML
    private TableColumn tableColumnCustomerAddress;
    @FXML
    private TableColumn tableColumnCustomerPhone;
    @FXML
    private TableColumn tableColumnAppointmentId;    
    @FXML
    private TableColumn tableColumnAppointmentTitle;
    @FXML
    private TableColumn tableColumnAppointmentLocation;
    @FXML
    private TableColumn tableColumnAppointmentContact;
    @FXML
    private TableColumn tableColumnAppointmentType;
    @FXML
    private TableColumn tableColumnAppointmentStart;
    @FXML
    private TableColumn tableColumnAppointmentEnd;
    @FXML
    private TableColumn tableColumnAppointmentCustomerId;
    @FXML
    private TableColumn tableColumnAppointmentUserId;
    @FXML
    private TableColumn tableColumnContactAppointmentId;
    @FXML
    private TableColumn tableColumnContactTitle;
    @FXML
    private TableColumn tableColumnContactType;
    @FXML
    private TableColumn tableColumnContactDescription;
    @FXML
    private TableColumn tableColumnContactStart;
    @FXML
    private TableColumn tableColumnContactEnd;
    @FXML
    private TableColumn tableColumnContactCustomerId; 
    @FXML
    private Label labelLocal;
    @FXML
    private Label labelEST;
    @FXML
    private Label labelUTC;
    @FXML
    private ComboBox comboBoxContact;
    
    private CustomerDAO customerDAO = new CustomerDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private ContactDAO contactDAO = new ContactDAO();
    
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    
    private static Customer customerToBeModified;
    private static Appointment appointmentToBeModified;
    
    /**
     * Initializes the main menu scene
     * LAMBDA: The contactName observable list utilizes a lambda to populate itself
     * with the names of all contacts. The lambda requires less lines of code than a for loop
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        //Displays all customers and appointments in the database        
        allCustomers.setAll(customerDAO.getAll());

        allAppointments.setAll(appointmentDAO.toLocal(appointmentDAO.getAll())); 
        
        allContacts.setAll(contactDAO.getAll());
        
        tableColumnCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableColumnCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableColumnCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableViewCustomers.setItems(allCustomers);
        
        tableColumnAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));        
        tableColumnAppointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableColumnAppointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        tableColumnAppointmentContact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        tableColumnAppointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnAppointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        tableColumnAppointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        tableColumnAppointmentCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableColumnAppointmentUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tableViewAppointments.setItems(allAppointments);   
        
        tableColumnContactAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        tableColumnContactTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableColumnContactType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnContactDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableColumnContactStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        tableColumnContactEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        tableColumnContactCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        ObservableList<String> contactName = FXCollections.observableArrayList();     
        //Lambda
        allContacts.forEach((contact) -> contactName.add(contact.getName()));
        
        comboBoxContact.setItems(contactName);
        
        initRealTime();
    }    
    
    /**
     * Opens the add customer window
     * @param event
     * @throws IOException 
     */
    public void handleButtonAddCustomer(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddCustomerScene.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();   
    }
    
    /**
     * Opens the modify customer window and sends selected customer to 
     * the window
     * @param event
     * @throws IOException 
     */
    public void handleButtonModifyCustomer(ActionEvent event) throws IOException{
        customerToBeModified = (Customer)tableViewCustomers.getSelectionModel().getSelectedItem();
        
        if (customerToBeModified == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No customer selected");
            alert.setContentText("There is no customer selected.");
            alert.showAndWait();
        }else{       
            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyCustomerScene.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();   
        }
    }
    
    /**
     * Checks to see if the selected customer has any appointments, if so
     * tell user to delete them first then delete the customer
     * @param event
     * @throws IOException 
     */
    public void handleButtonDeleteCustomer(ActionEvent event) throws IOException{
        Customer customerToBeDeleted = (Customer)tableViewCustomers.getSelectionModel().getSelectedItem();
        ObservableList<Integer> customerAppointments = FXCollections.observableArrayList();
        
        if(customerToBeDeleted == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No customer selected");
            alert.setContentText("There is no customer selected.");
            alert.showAndWait();
        }else{
            for(int i = 0; i < allAppointments.size(); i++){
                if(customerToBeDeleted.getCustomerId() == allAppointments.get(i).getCustomerId()){
                    customerAppointments.add(allAppointments.get(i).getAppointmentId());
                }
            }
            if(customerAppointments.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete customer");
                alert.setContentText("Are you sure you would like to delete " + customerToBeDeleted.getName() + "?");
                Optional<ButtonType> answer = alert.showAndWait();
                if(answer.get() == ButtonType.OK){
                    customerDAO.delete(customerToBeDeleted.getCustomerId());
                    customerDAO.update();      
                    allCustomers.remove(customerToBeDeleted);
                    tableViewCustomers.setItems(allCustomers);
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Customer deleted");
                    alert2.setContentText("Customer " + customerToBeDeleted.getName() + " has been deleted.");
                    alert2.showAndWait();
                }  
            }else if(!customerAppointments.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cannot delete customer");
                alert.setContentText("Customer " + customerToBeDeleted.getName() + " has scheduled apointments: "
                                    + customerAppointments + ". Please delete them before deleting the customer.");
                Optional<ButtonType> answer = alert.showAndWait();
            }
        }
    }
    
    /**
     * Opens the Add Appointment scene
     * @param event
     * @throws IOException 
     */
    public void handleButtonAddAppointment(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddAppointmentScene.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();   
    }
    
    /**
     * Opens the modify appointment window and sends the appointment to be modified
     * @param event
     * @throws IOException 
     */    
    public void handleButtonModifyAppointment(ActionEvent event) throws IOException{
        appointmentToBeModified = (Appointment)tableViewAppointments.getSelectionModel().getSelectedItem();
        
        if (appointmentToBeModified == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No appointment selected");
            alert.setContentText("There is no appointment selected.");
            alert.showAndWait();
        }else{    
            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyAppointmentScene.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();   
        }
    }
    
    /**
     * Handles the delete button for appointments, effectively canceling the appointment
     * @param event
     * @throws IOException 
     */    
    public void handleButtonDeleteAppointment(ActionEvent event) throws IOException{
        Appointment appointmentToBeDeleted = (Appointment)tableViewAppointments.getSelectionModel().getSelectedItem();
        if(appointmentToBeDeleted == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No appointment selected");
            alert.setContentText("There is no appointment selected.");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel appointment");
            alert.setContentText("Are you sure you would like to Cancel " + appointmentToBeDeleted.getAppointmentId() +
                                 " " + appointmentToBeDeleted.getType() + "?");
            Optional<ButtonType> answer = alert.showAndWait();
            if(answer.get() == ButtonType.OK){
                appointmentDAO.delete(appointmentToBeDeleted.getAppointmentId());
                appointmentDAO.update();      
                allAppointments.remove(appointmentToBeDeleted);
                tableViewAppointments.setItems(allAppointments);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Appointment canceled");
                alert2.setContentText("Appointment " + appointmentToBeDeleted.getAppointmentId() +
                                       " " + appointmentToBeDeleted.getType()+ " has been canceled.");
                alert2.showAndWait();
            }            
        }
    }
    
    /**
     * Handles the open calendar button
     * @param event
     * @throws IOException 
     */   
    public void handleButtonOpenCalendar(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/CalendarScene.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();  
    }
    
    /**
     * Handles the open report button
     * @param event
     * @throws IOException 
     */   
    public void handleButtonOpenReports(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/ReportScene.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();  
    }
    
    /**
     * Handles the contact combo box
     * @param event
     * @throws IOException 
     */
    public void handleComboBoxContact(ActionEvent event) throws IOException{
        int contactId = 0;
        String contact = (String)comboBoxContact.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> contactAppointment = FXCollections.observableArrayList();
         
        for(int i = 0; i < allContacts.size(); i++){
            if(contact == allContacts.get(i).getName()){
                contactId = allContacts.get(i).getContactId();
            }
        }
        
        for(int i = 0; i < allAppointments.size(); i++){
            if(contactId == allAppointments.get(i).getContactId()){
                contactAppointment.add(allAppointments.get(i));
            }
        }
        
        tableViewContactSchedule.setItems(contactAppointment);
    }
    
    /**
     * Handles the exit button
     * @param event
     * @throws IOException 
     */
    public void handleButtonExit(ActionEvent event) throws IOException{
        System.out.println("You clicked the exit button!");
        JDBC.closeConnection();
        System.exit(0);
    }
    
    /**
     * Gets the customers for the tableViewCustomers
     * @throws SQLException 
     */
    public void getCustomers() throws SQLException{        
        try{
            DBQuery.setStatement(JDBC.getConnection());
        }catch (SQLException e){
            e.printStackTrace();
        }
        Statement statement = DBQuery.getStatement();
        
        String sql = "SELECT * FROM client_schedule.countries";
        
        statement.execute(sql);
        ResultSet rs = statement.getResultSet();
        
        while(rs.next()){
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            LocalDate date = rs.getDate("Create_Date").toLocalDate();
            LocalTime time = rs.getTime("Create_Date").toLocalTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            System.out.println(countryId + " " + countryName + " " + date + " " + time + " " + createdBy + " " + lastUpdate);
        }
    }
    
    /**
     * Initializes the time for each time zone used within the program
     * LAMBDA: A lambda is used in the constructor parameter of the keyframe object
     * breaking up the expression increases the readability of the code
     */
    public void initRealTime(){
        //Lambda used on the line below
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            //Creates a time object for each time zone
            LocalDateTime currentLocalTime = LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toLocalDateTime();
            LocalDateTime currentESTlTime = LocalDateTime.now().atZone(ZoneOffset.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
            LocalDateTime currentUTCTime = LocalDateTime.now().atZone(ZoneOffset.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            labelLocal.setText(currentLocalTime.getHour() + ":" + currentLocalTime.getMinute() + ":" + currentLocalTime.getSecond());
            labelEST.setText(currentESTlTime.getHour() + ":" + currentESTlTime.getMinute() + ":" + currentESTlTime.getSecond());
            labelUTC.setText(currentUTCTime.getHour() + ":" + currentUTCTime.getMinute() + ":" + currentUTCTime.getSecond());
        }), new KeyFrame(Duration.seconds(1)));
        //Clock will play indefinitely
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
    /**
     * Gets the selected customer for the modify customer window
     * @return customerToBeModified
     */
    public static Customer getSelectedCustomer(){
        return customerToBeModified;
    }
    
    /**
     * Gets the selected appointment for the modify customer window
     * @return appointmentToBeModified
     */   
    public static Appointment getSelectedAppointment(){
        return appointmentToBeModified;
    }
}
