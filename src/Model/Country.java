/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;


/**
 * Class for countries in the database
 * @author Caleb Lugo
 */
public class Country {
    private int countryId;
    private String country;
    private LocalDate createDate;
    private String createdBy;
    private LocalDate lastUpdate;
    private String lastUpdatedBy;
    public Country(int countryId, String country, LocalDate createDate, String createdBy,
                    LocalDate lastUpdate, String lastUpdatedBy){
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    /**
     * Returns the country Id
     * @return countryId
     */
    public int getCountryId(){
        return countryId;
    }
    
    /**
     * Sets the country Id
     * @param countryId 
     */
    public void setCountryId(int countryId){
        this.countryId = countryId;
    }
    
    /**
     * Returns the country
     * @return country
     */
    public String getCountry(){
        return country;
    }
    
    /**
     * Sets the country
     * @param country 
     */
    public void setCountry(String country){
        this.country = country;
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
