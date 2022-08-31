/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controller for calendar scene
 * @author Caleb Lugo
 */
public class CalendarController implements Initializable{    
    @FXML
    private TableView tableViewCalendar;
    @FXML
    private TableColumn tableColumnAppointmentId;
    @FXML
    private TableColumn tableColumnTitle;
    @FXML
    private TableColumn tableColumnDescription;
    @FXML
    private TableColumn tableColumnLocation;
    @FXML
    private TableColumn tableColumnContact;
    @FXML
    private TableColumn tableColumnType;
    @FXML
    private TableColumn tableColumnStart;
    @FXML
    private TableColumn tableColumnEnd;
    @FXML
    private TableColumn tableColumnCustomerId;
    @FXML
    private TableColumn tableColumnUserId;
    @FXML
    private Label labelMonth;
    @FXML
    private Label labelDays;
    
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * Initializes the calendar scene
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allAppointments.setAll(appointmentDAO.toLocal(appointmentDAO.getAll()));               
        
        tableColumnAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableColumnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        tableColumnContact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        tableColumnEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        tableColumnCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableColumnUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        
        getWeekSchedule();
    }
        
    /**
     * Handles event if the week radio button is selected
     * @param event
     * @throws IOException 
     */
    public void handleRadioButtonWeek(ActionEvent event) throws IOException{
        getWeekSchedule();
    }
    
    /**
     * Handles event if the month radio button is selected
     * @param event
     * @throws IOException 
     */
    public void handleRadioButtonMonth(ActionEvent event) throws IOException{
        getMonthSchedule();
    }
    
    /**
     * Exits the calendar and returns to the main menu
     * @param event
     * @throws IOException 
     */
    public void handleButtonReturn(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuScene.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();   
    }   
    
    /**
     * Gathers all appointments within a week and displays them via the tableViewCalendar
     */
    public void getWeekSchedule(){
        labelMonth.setVisible(false);
        
        ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
        
        //Grabs current time, alters it so the data is set to midnight
        LocalDateTime time = LocalDateTime.now();
        time = time.minusHours(time.getHour());
        time = time.minusMinutes(time.getMinute());
        time = time.minusSeconds(time.getSecond());
        time = time.minusNanos(time.getNano());        
        
        //Sets date to monday, as monday is the beginning of the week
        for(int i = 0; i < 7; i++){
            if(time.minusDays(i).getDayOfWeek().getValue() == 1){
                time = time.minusDays(i);
            }
        }
        
        LocalDateTime startTime = time;                
        LocalDateTime endTime = startTime.plusDays(6);
        
        for(int i = 0; i < allAppointments.size(); i++){
            if(allAppointments.get(i).getStart().isBefore(endTime) && allAppointments.get(i).getStart().isAfter(startTime)){
                weekAppointments.add(allAppointments.get(i));
            }
        }        
        tableViewCalendar.setItems(weekAppointments);
        String labelText = startTime.getDayOfWeek() + " " + String.valueOf(startTime.getDayOfMonth()) + 
                           " - " + endTime.getDayOfWeek() + " " + String.valueOf(endTime.getDayOfMonth());
        labelDays.setText(labelText);
        labelDays.setVisible(true);
    }
    
    /**
     * Gathers all appointments for the current month and displays them via the tableViewCalendar
     */
    public void getMonthSchedule(){
        labelDays.setVisible(false);
        
        ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();

        LocalDateTime time = LocalDateTime.now();
        
        for(int i = 0; i < allAppointments.size(); i++){
            if(allAppointments.get(i).getStart().getMonth() == time.getMonth()){
                monthAppointments.add(allAppointments.get(i));
            }
        }
        tableViewCalendar.setItems(monthAppointments);
        labelMonth.setText(time.getMonth().toString());
        labelMonth.setVisible(true);
    }
}
