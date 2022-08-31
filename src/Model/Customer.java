/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;

/**
 * Class for customers in the database
 * @author Caleb Lugo
 */
public class Customer {
    private int customerId;
    private String name;
    private String address;
    private String postCode;
    private String phoneNumber;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    public int divisionId;
    public Customer(int customerId, String name, String address, String postCode,
                    String phoneNumber, LocalDateTime createDate, String createdBy,
                    LocalDateTime lastUpdate, String lastUpdatedBy, int divisionId){
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postCode = postCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }    
    
    /**
     * Returns the customer Id
     * @return customerId
     */
    public int getCustomerId(){
        return customerId;
    }
    
    /**
     * Sets the customer Id
     * @param customerId 
     */
    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }
    
    /**
     * Returns the customer name
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
     * Returns the address
     * @return address
     */
    public String getAddress(){
        return address;        
    }
    
    /**
     * Sets the address
     * @param address 
     */
    public void setAddress(String address){
        this.address = address;
    }
    
    /**
     * Returns the post code
     * @return postCode
     */
    public String getPostCode(){
        return postCode;
    }
    
    /**
     * Sets the post code
     * @param postCode 
     */
    public void setPostCode(String postCode){
        this.postCode = postCode;
    }
    
    /**
     * Returns the phone number
     * @return phoneNumber
     */
    public String getPhoneNumber(){
        return phoneNumber;
    }
    
    /**
     * Sets the phone number
     * @param phoneNumber 
     */
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
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
    public LocalDateTime getLastUpdate(){
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
     * Returns the division ID
     * @return divisionId
     */
    public int getDivisionId(){
        return divisionId;
    }
    
    /**
     * Sets the division ID
     * @param divisionId 
     */
    public void setDivisionId(int divisionId){
        this.divisionId = divisionId;
    }
}
