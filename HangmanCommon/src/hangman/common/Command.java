/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.common;

import java.io.Serializable;

/**
 *
 * @author Algirdas
 */
public class Command implements Serializable {
    public ServerCommands name;
    public String data;

    public Command(ServerCommands name) {
        this(name, "");
    }
   
    public Command(ServerCommands name, String data) {
        this.name = name;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Command{" + "name=" + name + ", data=" + data + '}';
    }
    
}
