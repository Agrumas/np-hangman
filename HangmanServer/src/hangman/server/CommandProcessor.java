/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.server;

import hangman.common.Command;
import hangman.common.Result;

/**
 *
 * @author Algirdas
 */
public class CommandProcessor {

    protected GameManager game;

    public CommandProcessor(GameManager game) {
        this.game = game;
    }

    public Result process(Command cmd, Player player, SocketHandler connection) {
        // here commands with logic meets, logic should be in GameManager
        switch (cmd.name) {
            case Login:
                // @Todo handle duplicate names
                // for example here we add a new player
                Player newPlayer = game.register(cmd.data);
                connection.setPlayer(newPlayer);
                return cmd.result("OK");
            case Quit:
                System.out.println("Player leaved");
                return cmd.result("OK");
            case StartGame:
                game.startGame(player);
                return cmd.result("STARTED", player.getStatus());
        }
        return null;
    }
}
