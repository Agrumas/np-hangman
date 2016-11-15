/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.client;

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

/**
 *
 * @author Algirdas
 */
public class Login {

    public interface LoginResponse {

        void onLogin(Object error, Connection connection);
    }

    protected Scene scene;

    public Login(LoginResponse loginCb) {
        Button btn = new Button();
        btn.setText("Connect");
        Button start = new Button();
        start.setText("Start Game");

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
        serverDetailsBox.getChildren().addAll(serverIpGroup, usernameGroup, btn, start);
        serverDetailsBox.setSpacing(10);
        serverDetailsBox.setAlignment(Pos.TOP_CENTER);

        Connection connection = new Connection();
        btn.setOnAction((ActionEvent event) -> {
            connection.setConnection(serverIpField.getText(), new Integer(serverPortField.getText()), usernameField.getText());
            connection.setConnectionCb((Result result) -> loginCb.onLogin(null, connection), (Result result) -> loginCb.onLogin(result, null));
            new Thread(connection).start();
        });
        
        start.setOnAction((ActionEvent event) -> {
            connection.execute(ServerCommands.StartGame, (result) -> {
                System.out.println(result);
            });
        });

        root.getChildren().addAll(serverDetailsBox);

        scene = new Scene(root, 300, 250);
    }

    public void display(Stage primaryStage) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
