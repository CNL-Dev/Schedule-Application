/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import javafx.collections.ObservableList;

/**
 * Generic interface for DAO java files
 * @author Caleb Lugo
 * @param <T>
 */
public interface DAOImpl<T> {
    /**
     * Gets all objects from database
     * @return allObjects
     */
    public ObservableList<T> getAll();
    /**
     * Returns a specific object from the database via ID
     * @param i
     * @return object
     */
    public T get(int i);
    /**
     * Refreshes the observableList objects are stored in
     */
    public void update();
    /**
     * Deletes an object via ID
     * @param i 
     */
    public void delete(int i);
}
