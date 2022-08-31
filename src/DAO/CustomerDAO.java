/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Customer;
import Utility.DBQuery;
import Utility.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DAO for customer
 * Check DAOImpl for details on each function
 * @author Caleb Lugo
 */
public class CustomerDAO implements DAOImpl{
    ObservableList<Customer> customers = FXCollections.observableArrayList();
    
    /**
     * Populates the customers observable list with data from the 
     * database
     * @return customers
     */
    @Override
    public ObservableList getAll() {
        try{
            DBQuery.setStatement(JDBC.getConnection());
            Statement statement = DBQuery.getStatement();
        
            String sql = "SELECT * FROM client_schedule.customers";
        
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            
            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostCode = rs.getString("Postal_Code");
                String customerPhoneNum = rs.getString("Phone");
                LocalDateTime customerCreateDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String customerCreatedBy = rs.getString("Created_By");
                LocalDateTime customerLastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String customerLastUpdatedBy = rs.getString("Last_Updated_By");
                int customerDivisionId = rs.getInt("Division_ID");
                customers.add(new Customer(customerId, customerName, customerAddress,
                              customerPostCode, customerPhoneNum, customerCreateDate,
                              customerCreatedBy, customerLastUpdate, customerLastUpdatedBy,
                              customerDivisionId));
            }            
        }catch (SQLException e){
            e.printStackTrace();
        }      
        return customers;
    }

    @Override
    public Object get(int i) {
        try{
            DBQuery.setStatement(JDBC.getConnection());
            Statement statement = DBQuery.getStatement();
        
            String sql = "SELECT * FROM client_schedule.customers WHERE Customer_ID='" + i + "'";
        
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostCode = rs.getString("Postal_Code");
            String customerPhoneNum = rs.getString("Phone");
            LocalDateTime customerCreateDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String customerCreatedBy = rs.getString("Created_By");
            LocalDateTime customerLastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String customerLastUpdatedBy = rs.getString("Last_Updated_By");
            int customerDivisionId = rs.getInt("Division_ID");
            Customer tempCustomer = new Customer(customerId, customerName, customerAddress,
                                                 customerPostCode, customerPhoneNum, customerCreateDate,
                                                 customerCreatedBy, customerLastUpdate, customerLastUpdatedBy,
                                                 customerDivisionId);
            return tempCustomer;
            
        }catch(SQLException e){
            e.printStackTrace();
        }        
        return null;
    }

    @Override
    public void update() {
        customers.removeAll(customers);
        getAll();
    }

    @Override
    public void delete(int i) {
        try{
            DBQuery.setStatement(JDBC.getConnection());
            Statement statement = DBQuery.getStatement();
        
            String sql = "DELETE FROM client_schedule.customers WHERE Customer_ID ='" + i + "'";
        
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
