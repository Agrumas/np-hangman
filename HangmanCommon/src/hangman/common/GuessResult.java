package hangman.common;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cosar
 */
public enum GuessResult implements Serializable {
    Correct, Wrong, Guessed, Failed, Duplicate
}