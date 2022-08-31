/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionDAO;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import Utility.DBQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for add customer scene
 * @author Caleb Lugo
 */
public class AddCustomerController implements Initializable{
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldAddress;
    @FXML
    private TextField textFieldPostalCode;
    @FXML
    private TextField textFieldPhoneNumber;
    @FXML
    private ComboBox comboBoxDivision;
    @FXML
    private ComboBox comboBoxCountry;
    
    private ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
    private ObservableList<Country> allCountries = FXCollections.observableArrayList();
    
    private String selectedCountry;
    private int selectedCountryId;
    
    private CountryDAO countryDAO = new CountryDAO();  
    private FirstLevelDivisionDAO divisionDAO = new FirstLevelDivisionDAO();
    
    
    /**
     * Initializes the add customer scene
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Gets the countries and divisions for the combo boxes       
        ObservableList<String> countryString = FXCollections.observableArrayList();
        allCountries.setAll(countryDAO.getAll());        
        for(int i = 0; i < allCountries.size(); i++){
            countryString.add(allCountries.get(i).getCountry());
        }
        comboBoxCountry.setItems(countryString);      
        allDivisions.setAll(divisionDAO.getAll());
    }
    
    public void handleCountryEvent(ActionEvent event) throws IOException{
        ObservableList<String> divisionString = FXCollections.observableArrayList();
        comboBoxDivision.setDisable(false);
        selectedCountry = (String)comboBoxCountry.getSelectionModel().getSelectedItem();        
        for(int i = 0; i < allCountries.size(); i++){
            if(selectedCountry == allCountries.get(i).getCountry()){
                selectedCountryId = allCountries.get(i).getCountryId();
            }
        }
        
        for(int i = 0; i < allDivisions.size(); i++){
            if(selectedCountryId == allDivisions.get(i).getCountryId()){  
                divisionString.add(allDivisions.get(i).getDivision());
                comboBoxDivision.setItems(divisionString);                
            }
        }
    }
    
    /**
     * Handles the save button action
     * @param event 
     * @throws java.io.IOException 
     */
    public void handleButtonSave(ActionEvent event) throws IOException{        
        try{
            int id = generateUniqueId();
            String name = textFieldName.getText();
            String address = textFieldAddress.getText();
            String postalCode = textFieldPostalCode.getText();
            String phoneNumber = textFieldPhoneNumber.getText();    
            //Zoned to UTC for database
            LocalDateTime createDate = LocalDateTime.now().atZone(ZoneOffset.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            String createdBy = "test";
            LocalDateTime lastUpdated = LocalDateTime.now().atZone(ZoneOffset.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            String lastUpdatedBy = "test";
            int divisionId = getDivisionId();
            
            Statement statement = DBQuery.getStatement();
        
            String sql = "INSERT INTO client_schedule.customers(Customer_ID, Customer_Name,"
                                      + " Address, Postal_Code, Phone, Create_Date,"
                                      + " Created_By, Last_Update, Last_Updated_By, Division_ID)"
                                      + "VALUES('" + id + "'," 
                                      + "'" + name + "'," 
                                      + "'" + address + "',"
                                      + "'" + postalCode + "'," 
                                      + "'" + phoneNumber + "'," 
                                      + "'" + createDate + "'," 
                                      + "'" + createdBy + "'," 
                                      + "'" + lastUpdated + "',"
                                      + "'" + lastUpdatedBy + "'," 
                                      + "'" + divisionId + "')";
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
                                
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuScene.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();  
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illegal entry");
            alert.setContentText("Illegal entry in one of the fields, please look at the entries and try again.");
            alert.showAndWait();
        }
    }
    
    /**
     * Handles the cancel button action
     * @param event 
     * @throws java.io.IOException 
     */
    public void handleButtonCancel(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuScene.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();   
    }   
    
    /**
     * Gets the division Id from the division name string
     * @return divisionId
     */
    public int getDivisionId(){
        int divisionId = 0;
        String division = (String)comboBoxDivision.getSelectionModel().getSelectedItem();
        for(int i = 0; i < allDivisions.size(); i++){
            if(division == allDivisions.get(i).getDivision()){
                divisionId = allDivisions.get(i).getDivisionId();
            }
        }
        return divisionId;
    }
    
    /**
     * Generates a unique ID sequential to the last ID used for customers
     * @return biggestId
     */
    public int generateUniqueId(){
        int biggestId = 0;
        CustomerDAO customerDAO = new CustomerDAO();
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        allCustomers.setAll(customerDAO.getAll());
        for(int i = 0; i < allCustomers.size(); i++){
            if(allCustomers.get(i).getCustomerId() >= biggestId){
                biggestId = allCustomers.get(i).getCustomerId();
            }
        }
        //Should be a unique ID
        return biggestId + 1;
    }            
}
