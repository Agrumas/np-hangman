/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.client;

import hangman.common.Result;

/**
 *
 * @author Algirdas
 */
@FunctionalInterface
public interface ResultCallback {
    public void invoke(Result result);
}
