/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.server;

import hangman.common.GuessResult;

/**
 *
 * @author Algirdas
 */
public class GameManager {

    protected PlayersList players;
    protected Dictionary dic;

    public GameManager() {
        players = new PlayersList();
        dic = new Dictionary();
        dic.readWordFile();
    }

    public Player register(String name) {
        return players.add(name);
    }

    public void unregister(Player p) {
        players.remove(p.name);
    }

    public String startGame(Player p) {
        return p.startGuessing(dic.getRandomWord());
    }

    public GuessResult guess(Player p, String data) {
        return p.guess(data);
    }

}
