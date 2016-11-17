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
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Algirdas
 */
public class SocketHandler implements Runnable {

    protected Socket clientSocket = null;
    protected Player player = null;
    protected CommandProcessor processor;

    public SocketHandler(Socket clientSocket, CommandProcessor processor) {
        this.clientSocket = clientSocket;
        this.processor = processor;
    }

    public void run() {
        try {
            // sending/receiving data between client and server
            InputStream input = clientSocket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(input);
            OutputStream output = clientSocket.getOutputStream();
            ObjectOutputStream ous = new ObjectOutputStream(output);

            System.out.println("New client connected");

            while (!clientSocket.isClosed()) {
                // ObjectInputStream, transfering not plain text, but objects
                // in our case Command object, its in common
                Command cmd = (Command) ois.readObject();

                System.out.println(cmd);
                // we got proccesor, which handles those commands
                Result answ = processor.process(cmd, player, this);
                if (answ == null) {
                    answ = cmd.error("ERR_NOT_FOUND", "Command is not found");
                }

                ous.writeObject(answ);
                ous.flush();
                if ("quit".equals(cmd.name)) {
                    ous.close();
                    ois.close();
                }
            }
        } catch (EOFException | SocketException e) {
            if (player != null) {
                // drop player from game
            }
            System.out.println("Connection closed");
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
