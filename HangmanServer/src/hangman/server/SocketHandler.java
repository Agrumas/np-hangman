/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.server;

import hangman.common.Command;
import hangman.common.Result;
import java.io.EOFException;
import java.io.InputStream;
import java.io.OutputStream;
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
public class SocketHandler implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText = null;
    protected Player player = null;
    protected GameManager game;

    protected static Result CMD_NOT_FOUND = new Result("ERR_NOT_FOUND", "Command is not found", true);

    public SocketHandler(Socket clientSocket, GameManager game) {
        this.clientSocket = clientSocket;
        this.game = game;
    }

    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();

            ObjectInputStream ois = new ObjectInputStream(input);
            OutputStream output = clientSocket.getOutputStream();
            ObjectOutputStream ous = new ObjectOutputStream(output);

            System.out.println("New client connected");

            while (!clientSocket.isClosed()) {
                Command cmd = (Command) ois.readObject();
                Result answ = CMD_NOT_FOUND;

                System.out.println(cmd);
                switch (cmd.name) {
                    case Login:
                        // should handle duplicate names
                        player = game.register(cmd.data);
                        answ = new Result("OK");
                        break;
                    case Quit:
                        answ = new Result("OK");
                        System.out.println("Player leaved");
                        break;
                }

                ous.writeObject(answ);
                ous.flush();
                if ("quit".equals(cmd.name)) {
                    ous.close();
                    ois.close();
                }
            }
        } catch (EOFException e) {
            if (player != null) {
                // drop player from game
                System.out.println("Connection closed");
            }
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
