/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.client.screen;

import hangman.client.Connection;
import hangman.client.Game;
import hangman.client.GameState;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import hangman.common.Result;
import hangman.common.ServerCommands;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Text;

/**
 *
 * @author Algirdas
 */
public class GameScreen implements Runnable {

    protected Stage primaryStage;
    protected Game game;

    protected Button btnStart;
    protected Text txtName, txtStatus, txtWord, txtGuessesLeft, txtScore;
    protected VBox boxGuess;

    public GameScreen(Stage primaryStage, Game game) {
        this.primaryStage = primaryStage;
        this.game = game;
    }

    @Override
    public void run() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("gameScreen.fxml"));
            show(root);
        } catch (IOException ex) {
            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void show(Parent root) {
        Scene scene = new Scene(root, 630, 351);
        primaryStage.setScene(scene);
        
        txtName = (Text) scene.lookup("#txtName");
        txtStatus = (Text) scene.lookup("#txtStatus");
        txtWord = (Text) scene.lookup("#txtWord");
        
        txtGuessesLeft = (Text) scene.lookup("#txtGuessesLeft");
        txtScore = (Text) scene.lookup("#txtScore");
        
        boxGuess = (VBox) scene.lookup("#boxGuess");
        btnStart = (Button) scene.lookup("#btnStart");

        btnStart.setOnAction((ActionEvent event) -> {
            game.start((gamesState) -> {
                btnStart.setDisable(true);
                boxGuess.setDisable(false);
                txtName.setText("Guess a letter or a word!");
                this.updateView(gamesState);
            });
        });
        primaryStage.show();
    }

    protected void updateView(GameState state) {
        txtName.setText(state.getName());
        txtWord.setText(state.getGuessWord());
        txtGuessesLeft.setText("" + state.getGuessesLeft());
        txtScore.setText("" + state.getScore());
    }
}
