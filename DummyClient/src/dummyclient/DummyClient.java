/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dummyclient;

import hangman.common.Command;
import hangman.common.Result;
import hangman.common.ServerCommands;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Algirdas
 */
public class DummyClient {

    /**
     * To debug this can be used
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Socket clientSocket;
        BufferedInputStream in;
        BufferedOutputStream out;
        ObjectOutputStream ous;
        ObjectInputStream ois;
        try {
            clientSocket = new Socket("127.0.0.1", 4444);       //open server socket
            in = new BufferedInputStream(clientSocket.getInputStream());      //input/output stream from server  
            out = new BufferedOutputStream(clientSocket.getOutputStream());

            ous = new ObjectOutputStream(out);
            System.out.println("Client up");
            // sends command to server
            ous.writeObject(new Command(ServerCommands.Login, "user"));
            ous.writeObject(new Command(ServerCommands.StartGame, "user"));
            ous.writeObject(new Command(ServerCommands.Guess, "e"));

            ous.flush();
            Result res = null;
            Result resSt = null;
            Result resGu = null;
            try {
                ois = new ObjectInputStream(in);
                res = (Result) ois.readObject();
                resSt = (Result) ois.readObject();
                resGu = (Result) ois.readObject();
                // gets response
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DummyClient.class.getName()).log(Level.SEVERE, null, ex);
            }

            clientSocket.close();

            System.out.println("Result: " + res);
            System.out.println("Result Start: " + resSt);
            System.out.println("Result Guess: " + resGu);
        } catch (IOException ex) {
            Logger.getLogger(DummyClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
