/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for the activity logger
 * This class will log certain activity into a text file
 * @author Caleb Lugo
 */
public class ActivityLogger {
    
    private static ActivityLogger logger = null;
    
    /**
     * Empty constructor
     */
    private void ActivityLogger(){
        
    }
    
    /**
     * Creates the logging file
     */
    public void createFile(){
        try{
            File file = new File("login_activity.txt");
            if(file.createNewFile()){
                System.out.println("File " + file.getName() + " created.");
            }else{
                System.out.println("File " + file.getName() + " already exists.");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Logs activity in the program
     * @param user
     * @param activity 
     */
    public void logActivity(String user, String activity){
        try{
            FileWriter writer = new FileWriter("login_activity.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(user + " " + activity + "\n");
            bufferedWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Allows access to logger
     * If no logger object exists, make one
     * @return logger
     */
    public static ActivityLogger getLogger(){
        if(logger == null){
            logger = new ActivityLogger();
        }
        return logger;
    }    
}