/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import Utility.ActivityLogger;
import Utility.DBQuery;
import Utility.JDBC;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for login scene
 * @author Caleb Lugo
 */
public class LoginController implements Initializable{
    @FXML
    private Label labelUsername;
    @FXML
    private Label labelPassword;
    @FXML
    private Label labelLogin;
    @FXML
    private Label labelLocale;
    @FXML
    private Label labelFailedLogin;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private Button buttonEnter;
    @FXML
    private Button buttonExit;
    
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * Initializes the login scene
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelFailedLogin.setVisible(false);
        labelLocale.setText(ZoneId.systemDefault().toString());
        allAppointments.setAll(appointmentDAO.getAll());
        
        //Checks locale and switches language if neccessary
        try{
            ResourceBundle rb = ResourceBundle.getBundle("Utility/Nat", Locale.getDefault());
            labelLogin.setText(rb.getString("login"));
            labelUsername.setText(rb.getString("username"));
            labelPassword.setText(rb.getString("password"));
            labelFailedLogin.setText(rb.getString("failedLogin"));
            buttonEnter.setText(rb.getString("enter"));
            buttonExit.setText(rb.getString("exit"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Handles the Enter button action
     * @param event
     * @throws IOException 
     * @throws java.sql.SQLException 
     */
    public void handleButtonEnter(ActionEvent event) throws IOException, SQLException{
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        
        DBQuery.setStatement(JDBC.getConnection());
        Statement statement = DBQuery.getStatement();
        
        String sql = "SELECT * FROM client_schedule.users WHERE User_ID= 1";
        
        statement.execute(sql);
        ResultSet rs = statement.getResultSet();
        rs.next();
        
        //Verifies if user entered correct information
        if(username.equals(rs.getString("User_Name")) && password.equals(rs.getString("Password"))){
            ActivityLogger.getLogger().logActivity(username, ("logged in with the password " 
                                                   + password + " at " + LocalDateTime.now()));
            ObservableList<Appointment> appointmentStarting = FXCollections.observableArrayList();
            //Alerts the user upon login if an appointment will start within 15 minutes
            for(int i = 0; i < allAppointments.size(); i++){
                //Zoned to UTC                         
                ZonedDateTime utcZonedTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));                                                              
                
                long minute = ChronoUnit.MINUTES.between(utcZonedTime.toLocalDateTime(), allAppointments.get(i).getStart());                                              
                
                //Adds appointment to a list if it is about to start
                if(minute <= 15 && minute > 0){
                    appointmentStarting.add(allAppointments.get(i));                   
                }
            } 
            
            //Display no appointments if list is empty
            if(appointmentStarting.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No immediate appointments");
                alert.setContentText("No appointments starting soon.");
                alert.showAndWait();
                //Display appointments that are about to start
            }else if(!appointmentStarting.isEmpty()){
                for(int i = 0; i < appointmentStarting.size(); i++)
                {
                    //Zoned to UTC             
                    long minute = ChronoUnit.MINUTES.between(LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(), appointmentStarting.get(i).getStart());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment will start soon");
                    alert.setContentText("Appointment " + appointmentStarting.get(i).getAppointmentId() + " "
                                     + appointmentStarting.get(i).getStart().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
                                     + " will start within " + minute +  " minute(s).");
                    alert.showAndWait();
                }
            }
            
            //Go to the main menu
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuScene.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else{
            //Displays a message when the user enters incorrect credentials
            labelFailedLogin.setVisible(true);
            ActivityLogger.getLogger().logActivity(username, (" failed to log in with the password "
                                                   + password + " at " + LocalDateTime.now()));
        }
    }
    
    /**
     * Exits the program
     * @param event
     * @throws IOException 
     */
    public void handleButtonExit(ActionEvent event) throws IOException{
        System.exit(0);
    }  
}
