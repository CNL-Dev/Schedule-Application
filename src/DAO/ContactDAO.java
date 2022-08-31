/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Contact;
import Utility.DBQuery;
import Utility.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * DAO for contact
 * Check DAOImpl for details on each function
 * @author Caleb Lugo
 */
public class ContactDAO implements DAOImpl{
    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

    @Override
    public ObservableList getAll() {
        try{
            DBQuery.setStatement(JDBC.getConnection());
            Statement statement = DBQuery.getStatement();
        
            String sql = "SELECT * FROM client_schedule.contacts";
        
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            
            while(rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                contacts.add(new Contact(contactId, contactName, contactEmail));
            }            
        }catch (SQLException e){
            e.printStackTrace();
        }      
        return contacts;
    }

    //Unused
    @Override
    public Object get(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Unused
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Unused
    @Override
    public void delete(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
