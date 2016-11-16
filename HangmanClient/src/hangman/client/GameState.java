/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.client;

/**
 *
 * @author Algirdas
 */
public class GameState {

    public String name;
    public int score = 0;
    public String word;
    public String guessWord;
    public int guessesLeft = 0;
    public String guesses;

    public GameState(String name) {
        this.name = name;
    }

    public void updateState(String playerString) {
        if (!playerString.startsWith("Player")) {
            return;
        }
        
        playerString = playerString.substring(7, playerString.length() - 1);
        String[] keyValuePairs = playerString.split(",");

        for (String pair : keyValuePairs) {
            String[] entry = pair.split("=");
           
            String id = entry[0].trim();
            String value = entry.length == 2 ? entry[1].trim() : "";
            switch (id) {
                case "name":
                    name = value;
                    break;
                case "score":
                    score = new Integer(value);
                    break;
                case "guessesLeft":
                    guessesLeft = new Integer(value);
                    break;
                case "guessWord":
                    guessWord = value;
                    break;
                case "guesses":
                    guesses = value;
                    break;
                case "word":
                    word = value;
                    break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getWord() {
        return word;
    }

    public String getGuessWord() {
        return guessWord;
    }

    public int getGuessesLeft() {
        return guessesLeft;
    }

    public String getGuesses() {
        return guesses;
    }
}
