/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Cosar
 */
public class Dictionary {

    public ArrayList<String> dictionary;

    public void readWordFile() {
        dictionary = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("words.txt"));
            String word;
            //Read all words from the file and add them to dictionary
            while ((reader.readLine()) != null) {
                word = reader.readLine();
                dictionary.add(word);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRandomWord() {
        int idx = ThreadLocalRandom.current().nextInt(dictionary.size());
        return dictionary.get(idx).toLowerCase();
    }
}
