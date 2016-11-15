/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.server;

/**
 *
 * @author Algirdas
 */
public class GameManager {
    protected PlayersList players;

    public GameManager() {
        players = new PlayersList();
    }
       
    
    public Player register(String name){
        return players.add(name);
    }
    
    public String startGame(Player p){
        return p.startGuessing("new guess word");
    }
    
    public String guess(Player p, String data){
        return p.guess(data);
    }
}
