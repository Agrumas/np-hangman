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
     *
     * @param name
     */
    public Player(String name) {
        this.name = name;
    }

    public String startGuessing(String word) {
        this.word = word.toLowerCase();
        guessesLeft = word.length();
        guessWord = word.replaceAll(".", "-");
        guesses = "";
        return guessWord;
    }

    public GuessResult guess(String data) {
        data = data.toLowerCase();
        if (data.length() > 1) {
            if (data.equals(word)) {
                score++;
                return GuessResult.Guessed;
            }
        } else {
            // make the comparision
            guesses = guesses + data;
            StringBuilder myWord = new StringBuilder(guessWord);
            int pos = -1;
            while ((pos = word.indexOf(data, pos + 1)) != -1) {
                myWord.setCharAt(pos, data.charAt(0));
            }

            if (!guessWord.equals(myWord.toString())) {
                guessWord = myWord.toString();
                if (guessWord.equals(word)) {
                    score++;
                    return GuessResult.Guessed;
                }
                return GuessResult.Correct;
            }

        }
        if (guessesLeft > 0) {
            guessesLeft--;
            return GuessResult.Wrong;
        }
        score--;
        return GuessResult.Failed;
    }

    // based on this string client will show information about game
    public String getStatus() {
        return "Player{" + "name=" + name + ", score=" + score + ", guessWord=" + guessWord + ", guessesLeft=" + guessesLeft + ", guesses=" + guesses + '}';
    }
}
