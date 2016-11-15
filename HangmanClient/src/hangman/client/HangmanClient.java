/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.client;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Algirdas
 */
public class HangmanClient extends Application {
    
    protected Connection connection;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hangman");
        Login loginScreen = new Login((err, conn) -> {
            if(err != null) {
                System.out.println("Error when trying to connect!" + err);
                return;
            }
            System.out.println("User connected");
            connection = conn;
        });
        loginScreen.display(primaryStage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
