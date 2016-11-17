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
public class HangmanServer {

   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /**
         * GameManager is responsible for Hangman logic
         */
        GameManager game = new GameManager();
        new Thread(new Server(game)).start(); //server handles connection with client
    }
    
}
