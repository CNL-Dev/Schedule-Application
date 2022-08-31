/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.FirstLevelDivision;
import Utility.DBQuery;
import Utility.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * DAO for first level division
 * Check DAOImpl for details on each function
 * @author Caleb Lugo
 */
public class FirstLevelDivisionDAO implements DAOImpl{
    private ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();
    
    @Override
    public ObservableList getAll() {
        try{
            DBQuery.setStatement(JDBC.getConnection());
            Statement statement = DBQuery.getStatement();
        
            String sql = "SELECT * FROM client_schedule.first_level_divisions";
        
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            
            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                LocalDate divisionCreateDate = rs.getDate("Create_Date").toLocalDate();
                String divisionCreatedBy = rs.getString("Created_By");
                LocalDate divisionLastUpdate = rs.getDate("Last_Update").toLocalDate();
                String divisionLastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("COUNTRY_ID");
                divisions.add(new FirstLevelDivision(divisionId, division, divisionCreateDate,
                              divisionCreatedBy, divisionLastUpdate, divisionLastUpdatedBy,
                              countryId));
            }            
        }catch (SQLException e){
            e.printStackTrace();
        }      
        return divisions;    
    }

    @Override
    public Object get(int i) {
        try{
            DBQuery.setStatement(JDBC.getConnection());
            Statement statement = DBQuery.getStatement();
        
            String sql = "SELECT * FROM client_schedule.first_level_divisions WHERE Division_ID='" + i + "'";
        
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            
            int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                LocalDate divisionCreateDate = rs.getDate("Create_Date").toLocalDate();
                String divisionCreatedBy = rs.getString("Created_By");
                LocalDate divisionLastUpdate = rs.getDate("Last_Update").toLocalDate();
                String divisionLastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("COUNTRY_ID");
                FirstLevelDivision tempDivision = new FirstLevelDivision(divisionId, division, divisionCreateDate,
                              divisionCreatedBy, divisionLastUpdate, divisionLastUpdatedBy,
                              countryId);
            return tempDivision;
            
        }catch(SQLException e){
            e.printStackTrace();
        }        
        return null;
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
