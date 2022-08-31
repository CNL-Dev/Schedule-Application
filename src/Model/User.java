/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;

/**
 * Class for users in the database
 * @author Caleb Lugo
 */
public class User {
    private int userId;
    private String name;
    private String password;
    private LocalDate createDate;
    private String createdBy;
    private LocalDate lastUpdate;
    private String lastUpdatedBy;
    public User(int userId, String name, String password, LocalDate createDate, String createdBy,
                LocalDate lastUpdate, String lastUpdatedBy){
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    /**
     * Returns the user id
     * @return userId
     */
    public int getUserId(){
        return userId;        
    }
    
    /**
     * Sets the user id
     * @param userId 
     */
    public void setUserId(int userId){
        this.userId = userId;
    }
    
    /**
     * Returns the name
     * @return name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Sets the name
     * @param name 
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Returns the password
     * @return password
     */
    public String getPassword(){
        return password;
    }
    
    /**
     * Sets the password
     * @param password 
     */
    public void setPassword(String password){
        this.password = password;
    }
    
    /**
     * Returns the create date
     * @return createDate
     */
    public LocalDate getCreateDate(){
        return createDate;
    }
    
    /**
     * Sets the create date
     * @param createDate 
     */
    public void setCreateDate(LocalDate createDate){
        this.createDate = createDate;
    }
    
    /**
     * Returns the created by
     * @return 
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
    public LocalDate getLastUpdate(){
        return lastUpdate;
    }
    
    /**
     * Sets the last update
     * @param lastUpdate 
     */
    public void setLastUpdate(LocalDate lastUpdate){
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
}
