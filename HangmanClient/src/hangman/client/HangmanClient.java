/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.client;

import hangman.client.screen.GameScreen;
import hangman.client.screen.Login;
import hangman.common.Result;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Algirdas
 */
public class HangmanClient extends Application {

    protected Connection connection;
    protected Game game;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hangman");
        Login loginScreen = new Login((err, game) -> {
            if (err != null) {
                Result result = (Result) err;
                JOptionPane.showMessageDialog(null, result.getData(), "Error when trying to connect!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            System.out.println("User connected");
            this.game = game;
            Platform.runLater(new GameScreen(primaryStage, game));
        });
        loginScreen.display(primaryStage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}
