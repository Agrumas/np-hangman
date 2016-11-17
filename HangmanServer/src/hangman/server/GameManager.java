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
    protected Dictionary dic;

    public GameManager() {
        players = new PlayersList();
        dic = new Dictionary();
        dic.readWordFile();
        dic.getRandomWord();
        
    }
       
    
    public Player register(String name){
        return players.add(name);
    }
    
    public String startGame(Player p){
        // some kind of Dictionary class is needed to read all words and return one randomly
        dic.readWordFile();
        return p.startGuessing(dic.getRandomWord());
    }
    
    public String guess(Player p, String data){
        return p.guess(data).toString();
    }
    
}
