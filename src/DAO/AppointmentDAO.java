/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Appointment;
import Utility.DBQuery;
import Utility.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DAO for Appointment
 * Check DAOImpl for details on each function
 * @author Caleb Lugo
 */
public class AppointmentDAO implements DAOImpl {
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    
    @Override
    public ObservableList getAll() {
        try{
            DBQuery.setStatement(JDBC.getConnection());
            Statement statement = DBQuery.getStatement();
        
            String sql = "SELECT * FROM client_schedule.appointments";
        
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();   
            
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            
            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                Instant appointmentStart = rs.getTimestamp("Start", cal).toInstant();
                Instant appointmentEnd = rs.getTimestamp("End", cal).toInstant();                
                Instant appointmentCreateDate = rs.getTimestamp("Create_Date", cal).toInstant();                
                String appointmentCreatedBy = rs.getString("Created_By");
                Instant appointmentLastUpdate = rs.getTimestamp("Last_Update", cal).toInstant();                
                String appointmentLastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID"); 
                
                //Zoned to UTC
                LocalDateTime utcAppointmentStart = LocalDateTime.ofInstant(appointmentStart, ZoneOffset.UTC);
                LocalDateTime utcAppointmentEnd = LocalDateTime.ofInstant(appointmentEnd, ZoneOffset.UTC);
                LocalDateTime utcAppointmentCreateDate = LocalDateTime.ofInstant(appointmentCreateDate, ZoneOffset.UTC);
                LocalDateTime utcAppointmentLastUpdate = LocalDateTime.ofInstant(appointmentLastUpdate, ZoneOffset.UTC);
                                                             
                appointments.add(new Appointment(appointmentId, appointmentTitle, appointmentDescription,
                              appointmentLocation, appointmentType, utcAppointmentStart, utcAppointmentEnd, 
                              utcAppointmentCreateDate, appointmentCreatedBy, utcAppointmentLastUpdate, 
                              appointmentLastUpdatedBy, customerId, userId, contactId));
            }            
        }catch (SQLException e){
            e.printStackTrace();
        }      
        return appointments;
    }

    @Override
    public Object get(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update() {
        appointments.removeAll(appointments);
        getAll();
    }

    @Override
    public void delete(int i) {
        try{
            DBQuery.setStatement(JDBC.getConnection());
            Statement statement = DBQuery.getStatement();
        
            String sql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID ='" + i + "'";
        
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Converts the appointment start and end times to local
     * @param list
     * @return 
     */
    public ObservableList<Appointment> toLocal(ObservableList<Appointment> list){
        ObservableList<Appointment> localZonedAppointments = FXCollections.observableArrayList();
        localZonedAppointments.setAll(appointments);
        
        for(int i = 0; i < localZonedAppointments.size(); i++){
            ZonedDateTime localStart = localZonedAppointments.get(i).getStart().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime localEnd = localZonedAppointments.get(i).getEnd().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
            
            localZonedAppointments.get(i).setStart(localStart.toLocalDateTime());
            localZonedAppointments.get(i).setEnd(localEnd.toLocalDateTime());
        }
        
        return localZonedAppointments;
    }
}
