/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class allows for SQL statements in java
 * @author Caleb Lugo
 */
public class DBQuery {
    private static Statement statement; // Statement reference 
    
    /**
     * Creates a statement
     * @param conn
     * @throws SQLException 
     */
    public static void setStatement(Connection conn) throws SQLException{
        statement = conn.createStatement();
    }
    
    /**
     * Returns the statement
     * @return 
     */
    public static Statement getStatement(){
        return statement;
    }
}
