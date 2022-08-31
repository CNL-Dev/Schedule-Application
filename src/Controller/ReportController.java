/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionDAO;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Controller for the report scene
 * @author Caleb Lugo
 */
public class ReportController implements Initializable{
    @FXML
    private TextArea textAreaMonth;
    @FXML
    private TextArea textAreaCountry;
    
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private FirstLevelDivisionDAO divisionDAO = new FirstLevelDivisionDAO();
    private CountryDAO countryDAO = new CountryDAO();
    
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
    private ObservableList<Country> allCountries = FXCollections.observableArrayList();
    
    private String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                               "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    
    private String[] countries = {"U.S", "UK", "Canada"};
    
    /**
     * Initializes the report scene
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> typeList = new ArrayList<String>();
        ArrayList<String> newTypeList = new ArrayList<String>();
        
        allAppointments.setAll(appointmentDAO.getAll());
        allCustomers.setAll(customerDAO.getAll());
        allDivisions.setAll(divisionDAO.getAll());
        allCountries.setAll(countryDAO.getAll());
        
        
        ObservableList<String> appointmentsByMonth = FXCollections.observableArrayList();
        ObservableList<String> customersByCountry = FXCollections.observableArrayList();        
        
        //Gets all appointment types
        for(int i = 0; i < allAppointments.size(); i++){
            typeList.add(allAppointments.get(i).getType());
        }
        
        //Removes duplicate appointment types
        newTypeList.addAll(removeDuplicates(typeList));
        
        //Converts list to string array
        String[] arr = new String[newTypeList.size()];
        for(int i = 0; i < newTypeList.size(); i++){
            arr[i] = newTypeList.get(i);
        }
        
        //Gets all months and the number of appointments scheduled for the month
        for(int i = 0; i < months.length; i++){
            for(int j = 0; j < arr.length; j++){
                if(getMonths(months[i], arr[j]) != null){
                    appointmentsByMonth.add(getMonths(months[i], arr[j]));
                }
            }
        }
        
        //Gets all countries and amount of customers per country
        for(int i = 0; i < countries.length; i++){
            customersByCountry.add(getCountries(getCountryName(allCustomers.get(i).getDivisionId())));
        }
        
        textAreaMonth.setText(appointmentsByMonth.toString());
        textAreaCountry.setText(customersByCountry.toString());
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
     * Find the amount of appointments per month and how many of each type there are
     * @param month
     * @param type
     * @return monthAppointments
     */
    public String getMonths(String month, String type){
        int typeTally = 0;
        for(int i = 0; i < allAppointments.size(); i++){
            if(month.equals(allAppointments.get(i).getMonth())){
                if(type.equals(allAppointments.get(i).getType())){
                    typeTally++;
                }
            }
        }
        if(typeTally != 0){
            String monthAppointments = month + " - " + type + " - " + typeTally + "\n";
            return monthAppointments;
        }else{
            return null;
        }
        
    }
        
    /**
     * Returns the amount of customers per country
     * @param country
     * @return countryCustomers
     */
    public String getCountries(String country){
        int countryTally = 0;
        for(int i = 0; i < allCustomers.size(); i++){
            if(country.equals(getCountryName(allCustomers.get(i).getDivisionId()))){
                countryTally++;
            }
        }
        String countryCustomers = country + " Customers: " + countryTally  + "\n";
        return countryCustomers;
    }
    
    /**
     * Removes any duplicates from an array list
     * @param arrayList
     * @return arrayList
     */
    public ArrayList<String> removeDuplicates(ArrayList<String> arrayList){
        Set<String> set = new LinkedHashSet<>();
        set.addAll(arrayList);
        arrayList.clear();
        arrayList.addAll(set);
        return arrayList;
    }
    
    /**
     * Returns the name of the country from the customer
     * @param id
     * @return countryName
     */
    public String getCountryName(int id){
        int divisionId = id;
        int countryId = 0;
        String countryName = "";
        for(int i = 0; i < allDivisions.size(); i++){
            if(divisionId == allDivisions.get(i).getDivisionId()){
                countryId = allDivisions.get(i).getCountryId();
            }
        }
        
        for(int i = 0; i < allCountries.size(); i++){
            if(countryId == allCountries.get(i).getCountryId()){
                countryName = allCountries.get(i).getCountry();
            }
        }
        return countryName;
    }
}
