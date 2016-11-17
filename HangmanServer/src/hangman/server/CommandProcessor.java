/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.server;

import hangman.common.Command;
import hangman.common.GuessResult;
import hangman.common.Result;
import hangman.common.ServerCommands;

/**
 *
 * @author Algirdas
 */
public class CommandProcessor {

    protected GameManager game;
    private String data;

    public CommandProcessor(GameManager game) {
        this.game = game;
    }

    public Result process(Command cmd, Player player, SocketHandler connection) {
        if (player == null) {
            if (cmd.name == ServerCommands.Login) {
                Player newPlayer = game.register(cmd.data);
                if(newPlayer == null){
                    return cmd.error("NAME_IN_USE", "The name is already in use");
                }
                connection.setPlayer(newPlayer);
                return cmd.result("OK");
            }
            return null;
        }
        
        switch (cmd.name) {
            case Quit:
                System.out.println("Player leaved");
                game.unregister(player);
                return cmd.result("OK");
            case StartGame:
                game.startGame(player);
                //return cmd.result("STARTED", player.word);
                return cmd.result("STARTED", player.getStatus());
            case Guess:
                GuessResult result = game.guess(player, cmd.data);
                return cmd.result(result.toString(), player.getStatus());
        }
        return null;
    }
}
