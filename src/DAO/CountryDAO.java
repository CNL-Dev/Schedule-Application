/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Country;
import Utility.DBQuery;
import Utility.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * DAO for country
 * Check DAOImpl for details on each function
 * @author Caleb Lugo
 */
public class CountryDAO implements DAOImpl{
    private ObservableList<Country> countries = FXCollections.observableArrayList();
        
    @Override
    public ObservableList getAll() {
        try{
            DBQuery.setStatement(JDBC.getConnection());
            Statement statement = DBQuery.getStatement();
        
            String sql = "SELECT * FROM client_schedule.countries";
        
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            
            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                LocalDate countryCreateDate = rs.getDate("Create_Date").toLocalDate();
                String countryCreatedBy = rs.getString("Created_By");
                LocalDate countryLastUpdate = rs.getDate("Last_Update").toLocalDate();
                String countryLastUpdatedBy = rs.getString("Last_Updated_By");
                countries.add(new Country(countryId, country, countryCreateDate,
                              countryCreatedBy, countryLastUpdate, countryLastUpdatedBy));
            }            
        }catch (SQLException e){
            e.printStackTrace();
        }      
        return countries;
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
