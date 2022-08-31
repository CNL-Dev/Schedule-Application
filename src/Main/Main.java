/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Utility.ActivityLogger;
import Utility.JDBC;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main method of program
 * Opens the login screen
 * @author Caleb Lugo
 */
public class Main extends Application {
    
    /**
     * Opens the login scene
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScene.fxml"));        
        Scene scene = new Scene(root);        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Runs the program
     * @param args the command line arguments
     */
    public static void main(String[] args){         
        //Sets up logger for login screen
        ActivityLogger.getLogger().createFile();
        //Opens connection to database
        JDBC.openConnection();
        launch(args);
        //Closes connection to database
        JDBC.closeConnection();        
    }    
}
