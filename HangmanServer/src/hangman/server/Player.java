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
    public int score = 0;
    public String word;
    public String guessWord;
    public int guessesLeft = 0;
    public char[] guesses;

    public Player(String name) {
        this.name = name;
    }

    public String startGuessing(String word) {
        this.word = word;
        guessesLeft = word.length();
        guessWord = word.replace(".*", "-");
        guesses = new char[guessesLeft];
        return guessWord;
    }

    public String guess(String data) {
        return "";
    }

    public String getStatus() {
        return "Player{" + "name=" + name + ", score=" + score + ", guessWord=" + guessWord + ", guessesLeft=" + guessesLeft + ", guesses=" + guesses + '}';
    }
}
