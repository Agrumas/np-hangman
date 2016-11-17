/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.common;

import java.io.Serializable;

/**
 * here you should define all commands supported by server
 * @author Algirdas
 */
public enum ServerCommands implements Serializable {
    Login, Quit, StartGame, Guess
}
