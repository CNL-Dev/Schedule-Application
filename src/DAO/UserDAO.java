/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.User;
import Utility.DBQuery;
import Utility.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DAO for user
 * Check DAOImpl for details on each function
 * @author Caleb Lugo
 */
public class UserDAO implements DAOImpl{
    public ObservableList<User> users = FXCollections.observableArrayList();
    
    @Override
    public ObservableList getAll() {
        try{
            DBQuery.setStatement(JDBC.getConnection());
            Statement statement = DBQuery.getStatement();
        
            String sql = "SELECT * FROM client_schedule.users";
        
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            
            while(rs.next()){
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                LocalDate userCreateDate = rs.getDate("Create_Date").toLocalDate();
                String userCreatedBy = rs.getString("Created_By");
                LocalDate userLastUpdate = rs.getDate("Last_Update").toLocalDate();
                String userLastUpdatedBy = rs.getString("Last_Updated_By");
                users.add(new User(userId, userName, userPassword, userCreateDate,
                          userCreatedBy, userLastUpdate, userLastUpdatedBy));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }      
        return users;
    }

    @Override
    public Object get(int i) {
        try{
            DBQuery.setStatement(JDBC.getConnection());
            Statement statement = DBQuery.getStatement();
        
            String sql = "SELECT * FROM client_schedule.users WHERE User_ID='" + i + "'";
        
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            LocalDate userCreateDate = rs.getDate("Create_Date").toLocalDate();
            String userCreatedBy = rs.getString("Created_By");
            LocalDate userLastUpdate = rs.getDate("Last_Update").toLocalDate();
            String userLastUpdatedBy = rs.getString("Last_Updated_By");
            User tempUser = new User(userId, userName, userPassword, userCreateDate,
                                     userCreatedBy, userLastUpdate, userLastUpdatedBy);
            return tempUser;
            
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
