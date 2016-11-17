/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.client;

import hangman.common.ErrorCallback;
import hangman.common.Result;
import hangman.common.ResultCallback;
import hangman.common.ServerCommands;
import javafx.application.Platform;

/**
 *
 * @author Algirdas
 */
public class Game {

    protected Connection connection;
    protected GameState state;

    @FunctionalInterface
    public interface GameStateCallback {

        public void invoke(GameState gamesState);
    }

    public Game(String name, Connection connection) {
        this.connection = connection;
        this.state = new GameState(name);
    }

    public void setName(String name) {
        this.state.setName(name);
    }

    public GameState updateState(String stateStr) {
        state.updateState(stateStr);
        return state;
    }

    public void start(GameStateCallback callback) {
        start(callback, null);
    }
    
    public void start(GameStateCallback callback, ErrorCallback errorCb) {
        connection.execute(ServerCommands.StartGame, (Result result) -> {
            updateState(result.getData());
            Platform.runLater(() -> {
                callback.invoke(state);
            });
        }, handleError(errorCb));
    }

    protected ErrorCallback handleError(ErrorCallback errorCb) {
        if (errorCb != null) {
            return (Result result) -> {
                Platform.runLater(() -> {
                    errorCb.invoke(result);
                });
            };
        }
        return null;
    }
}
