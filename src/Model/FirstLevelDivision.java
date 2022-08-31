/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;

/**
 * Class for first level divisions in the database
 * @author Caleb Lugo
 */
public class FirstLevelDivision {
    private int divisionId;
    private String division;
    private LocalDate createDate;
    private String createdBy;
    private LocalDate lastUpdate;
    private String lastUpdatedBy;
    private int countryId;
    public FirstLevelDivision(int divisionId, String division, LocalDate createDate,
                              String createdBy, LocalDate lastUpdate, String lastUpdatedBy, int countryId){
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }
    
    /**
     * Returns the divisionId
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
    
    /**
     * Returns the division
     * @return division
     */
    public String getDivision(){
        return division;
    }
    
    /**
     * Sets the division
     * @param division 
     */
    public void setDivision(String division){
        this.division = division;
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
}
