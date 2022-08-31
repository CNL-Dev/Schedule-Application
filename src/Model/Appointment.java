/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;


/**
 * Class for appointments in the database
 * @author Caleb Lugo
 */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;
    public Appointment(int appointmentId, String title, String description, String location, String type,
                        LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, 
                        LocalDateTime lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId){
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }
    
    /**
     * Gets the appointment ID
     * @return appointmentId
     */
    public int getAppointmentId(){
        return appointmentId;
    }
    
    /**
     * Sets the appointment ID
     * @param appointmentId 
     */
    public void setAppointmentId(int appointmentId){
        this.appointmentId = appointmentId;
    }
    
    /**
     * Returns the title
     * @return title
     */
    public String getTitle(){
        return title;
    }
    
    /**
     * Sets the title
     * @param title 
     */
    public void setTitle(String title){
        this.title = title;
    }
    
    /**
     * Returns the description
     * @return description
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Sets the description 
     * @param description 
     */
    public void setDescription(String description){
        this.description = description;
    }
    /**
     * Returns the location
     * @return location
     */
    public String getLocation(){
        return location;
    }
    
    /**
     * Sets the location
     * @param location 
     */
    public void setLocation(String location){
        this.location = location;
    }
    
    /**
     * Returns the type
     * @return type
     */
    public String getType(){
        return type;
    }
    
    /**
     * Sets the type
     * @param type 
     */
    public void setType(String type){
        this.type = type;
    }
    
    /**
     * Returns the start
     * @return start
     */
    public LocalDateTime getStart(){
        return start;
    }
    
    /**
     * Sets the start
     * @param start 
     */
    public void setStart(LocalDateTime start){
        this.start = start;
    }
    
    /**
     * Returns the end
     * @return end
     */
    public LocalDateTime getEnd(){
        return end;
    }
    
    /**
     * Sets the end
     * @param end 
     */
    public void setEnd(LocalDateTime end){
        this.end = end;
    }
    
    /**
     * Returns the create date
     * @return createDate
     */
    public LocalDateTime getCreateDate(){
        return createDate;
    }
    
    /**
     * Sets the create date
     * @param createDate 
     */
    public void setCreateDate(LocalDateTime createDate){
        this.createDate = createDate;
    }
    
    /**
     * Returns the created by
     * @return createdBy
     */
    public String getCreatedBy(){
        return createdBy;
    }
    
    /**
     * Sets the created by
     * @param createdBy 
     */
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }
    
    /**
     * Returns the last update
     * @return lastUpdate
     */
    public LocalDateTime getlastUpdate(){
        return lastUpdate;
    }
    
    /**
     * Sets the last update
     * @param lastUpdate 
     */
    public void setLastUpdate(LocalDateTime lastUpdate){
        this.lastUpdate = lastUpdate;
    }
    
    /**
     * Returns the last updated by
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }
    
    /**
     * Sets the last updated by 
     * @param lastUpdatedBy 
     */
    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    /**
     * Gets the customer ID
     * @return customerId
     */
    public int getCustomerId(){
        return customerId;
    }
    
    /**
     * Sets the customer ID
     * @param customerId 
     */
    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }
    
    /**
     * Gets the user ID
     * @return userId
     */
    public int getUserId(){
        return userId;
    }
    
    /**
     * Sets the user ID
     * @param userId 
     */
    public void setUserId(int userId){
        this.userId = userId;
    }
    
    /**
     * Gets the contact ID
     * @return contactId
     */
    public int getContactId(){
        return contactId;
    }
    
    /**
     * Sets the contact ID
     * @param contactId 
     */
    public void setContactId(int contactId){
        this.contactId = contactId;
    }
    
    /**
     * Returns the month
     * @return month
     */
    public String getMonth(){
        return start.getMonth().toString();
    }
}
