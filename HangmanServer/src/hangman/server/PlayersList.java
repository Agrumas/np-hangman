/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.server;

import java.util.HashMap;

/**
 *
 * @author Algirdas
 */
public class PlayersList {
    protected HashMap<String, Player> list;

    public PlayersList() {
        list = new HashMap<>();
    }
    
    public synchronized Player add(String name){
        Player p = new Player(name);
        list.put(name, p);
        return p;
    }
    
    public synchronized void remove(String name){
        list.remove(name);
    }
}
