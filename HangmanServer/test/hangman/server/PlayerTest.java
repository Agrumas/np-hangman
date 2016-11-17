/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.server;

import hangman.common.GuessResult;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Cosar
 */
public class PlayerTest {

    public PlayerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of guess method, of class Player.
     */
    @Test
    public void testGuess() {
        System.out.println("guess");
        String data = "";
        Player player = new Player("");
        player.startGuessing("programming");
        player.guessWord = "-rogra--ing";
        GuessResult result = player.guess("m");
        assertEquals(GuessResult.Correct, result);
        result = player.guess("p");
        assertEquals(GuessResult.Guessed, result);
    }

    @Test
    public void testGuessFail() {
        System.out.println("guess");
        String data = "";
        Player player = new Player("");
        player.startGuessing("programming");
        player.guessWord = "-rogra--ing";
        
        GuessResult result = player.guess("e");
        assertEquals(GuessResult.Wrong, result);
        player.guessesLeft =0;
        result = player.guess("e");
        assertEquals(GuessResult.Failed, result);
    }

}
