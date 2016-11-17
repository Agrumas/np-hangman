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
public class Player {

    public String name;
    /**
     * Total score
     */
    public int score = 0;
    /**
     * Word to guess 'programming'
     */
    public String word;
    /**
     * Word visible to player 'pro----'
     */
    public String guessWord;
    /**
     * Time how much he can guess till loosing
     */
    public int guessesLeft = 0;
    /**
     * Letters he tried 'wq..'
     */
    public String guesses;

    /**
     * Tracks state of players game
     * @param name 
     */
    public Player(String name) {
        this.name = name;
    }

    public String startGuessing(String word) {
        this.word = word;
        guessesLeft = word.length();
        guessWord = word.replace(".*", "-");
        guesses = "";
        return guessWord;
    }

    public String guess(String data) {
        if(true){
        }
        
        return getStatus();
    }

    // based on this string client will show information about game
    public String getStatus() {
        return "Player{" + "name=" + name + ", score=" + score + ", guessWord=" + guessWord + ", guessesLeft=" + guessesLeft + ", guesses=" + guesses + '}';
    }
}
