/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.client.screen;

import hangman.client.Game;
import hangman.client.GameState;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;

/**
 *
 * @author Algirdas
 */
public class GameScreen implements Runnable {

    protected Stage primaryStage;
    protected Game game;

    protected Button btnStart, btnGuess;
    protected Text txtName, txtStatus, txtWord, txtGuessesLeft, txtScore;
    protected HBox boxGuess;
    protected TextField inGuess;

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

        boxGuess = (HBox) scene.lookup("#boxGuess");
        btnStart = (Button) scene.lookup("#btnStart");
        inGuess = (TextField) scene.lookup("#inGuess");
        btnGuess = (Button) scene.lookup("#btnGuess");

        btnStart.setVisible(false);

        Game.GameStateCallback uiGameStart = (gamesState) -> {
            btnStart.setDisable(true);
            boxGuess.setDisable(false);
            txtStatus.setText("Guess a letter or a word!");
            this.updateView(gamesState);
        };

        btnStart.setOnAction((ActionEvent event) -> {
            btnStart.setVisible(false);
            game.start(uiGameStart);
        });

        EventHandler<ActionEvent> onSubmit = (ActionEvent event) -> {
            String word = inGuess.getText().trim().toLowerCase();
            if (word.isEmpty()) {
                return;
            }
            
            if (!word.matches("[a-zA-Z]+")) {
                txtStatus.setText("Invalid character, only letters are allowed!");
                inGuess.setText("");
                return;
            }
            boxGuess.setDisable(true);
            game.guess(word, (gamesState) -> {
                boxGuess.setDisable(false);
                updateStatusText(gamesState);
                inGuess.setText("");
                this.updateView(gamesState);
            }, (error) -> {
                boxGuess.setDisable(false);
                showErrorModal("Can not submit guess", error.getData());
            });
        };

        btnGuess.setOnAction(onSubmit);
        inGuess.setOnAction(onSubmit);

        game.start((gamesState) -> {
            uiGameStart.invoke(gamesState);
            primaryStage.show();
        });
    }

    protected String getGuesses(GameState gamesState) {
        String guessed = gamesState.guesses.toUpperCase();
        char[] ar = guessed.toCharArray();
        Arrays.sort(ar);
        return String.valueOf(ar);
    }

    protected void showErrorModal(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    protected void updateStatusText(GameState state) {
        switch (state.getGuessResult()) {
            case Correct:
                txtStatus.setText("Your guess is correct!");
                break;
            case Duplicate:
                txtStatus.setText("Letter was tried. Letters: " + getGuesses(state));
                break;
            case Failed:
                txtStatus.setText("Game Over! The correct word was:");
                btnStart.setDisable(false);
                boxGuess.setDisable(true);
                btnStart.setVisible(true);
                break;
            case Guessed:
                txtStatus.setText("Congratulations! You have guessed the word.");
                btnStart.setVisible(true);
                btnStart.setDisable(false);
                boxGuess.setDisable(true);
                break;
            case Wrong:
                txtStatus.setText("Missed! Tried letters: " + getGuesses(state));
                break;
        }
    }

    protected void updateView(GameState state) {
        txtName.setText(state.getName());
        txtWord.setText(state.getGuessWord());
        txtGuessesLeft.setText("" + state.getGuessesLeft());
        txtScore.setText("" + state.getScore());
    }
}
