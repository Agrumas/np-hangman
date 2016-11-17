/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.client.screen;

import hangman.client.Connection;
import hangman.client.Game;
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
import javafx.geometry.Insets;

/**
 *
 * @author Algirdas
 */
public class Login {

    public interface LoginResponse {

        void onLogin(Object error, Game game);
    }

    protected Scene scene;

    public Login(LoginResponse loginCb) {
        Button btnStart = new Button();
        btnStart.setText("Start Game");

        Label serverIpLabel = new Label("Server:");
        serverIpLabel.setFont(new Font("", 20));
        TextField serverIpField = new TextField();
        serverIpField.setText("127.0.0.1");
        HBox serverIpGroup = new HBox();
        TextField serverPortField = new TextField();
        serverPortField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        serverPortField.setText("4444");
        serverPortField.setMaxWidth(50);
        serverIpGroup.getChildren().addAll(serverIpLabel, serverIpField, serverPortField);
        serverIpGroup.setSpacing(10);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("", 20));
        TextField usernameField = new TextField();
        usernameField.setText("Tester");
        HBox usernameGroup = new HBox();
        usernameGroup.getChildren().addAll(usernameLabel, usernameField);
        usernameGroup.setSpacing(10);

        StackPane root = new StackPane();
        VBox serverDetailsBox = new VBox();
        serverDetailsBox.setPadding(new Insets(60, 0, 0, 10));
        serverDetailsBox.getChildren().addAll(serverIpGroup, usernameGroup, btnStart);
        serverDetailsBox.setSpacing(10);
        serverDetailsBox.setAlignment(Pos.TOP_CENTER);

        Connection connection = new Connection();
        Game game = new Game("Guest", connection);
        btnStart.setOnAction((ActionEvent event) -> {
            game.setName(usernameField.getText());
            connection.setConnection(serverIpField.getText(), new Integer(serverPortField.getText()), usernameField.getText());
            connection.setConnectionCb((Result result) -> {
                game.updateState(result.getData());
                    loginCb.onLogin(null, game);
            }, (Result result) -> loginCb.onLogin(result, null));
            new Thread(connection).start();
        });

        root.getChildren().addAll(serverDetailsBox);

        scene = new Scene(root, 300, 250);
    }

    public void display(Stage primaryStage) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
