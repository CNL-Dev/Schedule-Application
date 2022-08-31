/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *Class for contacts in the database
 * @author Caleb Lugo
 */
public class Contact {
    private int contactId;
    private String name;
    private String email;
    public Contact(int contactId, String name, String email){
        this.contactId = contactId;
        this.name = name;
        this.email = email;
    }
    
    /**
     * Returns the contact Id
     * @return contactId
     */
    public int getContactId(){
        return contactId;
    }
    
    /**
     * Sets the contact Id
     * @param contactId 
     */
    public void setContactId(int contactId){
        this.contactId = contactId;
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
     * Returns the email
     * @return email
     */
    public String getEmail(){
        return email;
    }
    
    /**
     * Sets the email
     * @param email 
     */
    public void setEmail(String email){
        this.email = email;
    }
}
