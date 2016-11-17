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

    /**
     * PlayersList is more or less wrapper of HashMap
     */
    public PlayersList() {
        list = new HashMap<>();
    }

    public synchronized Player add(String name) {
        if (list.containsKey(name)) {
            return null;
        }
        return list.put(name, new Player(name));
    }

    public synchronized void remove(String name) {
        list.remove(name);
    }
}
