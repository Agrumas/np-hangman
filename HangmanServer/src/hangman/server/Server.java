/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Algirdas
 */
public class Server implements Runnable {

    private static final int PORT = 4444;
    
    protected CommandProcessor processor;

    public Server(GameManager game) {
        processor = new CommandProcessor(game);
    }

    @Override
    public void run() {
        boolean listening = true;
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server listen on port: " + PORT);
            while (listening) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new SocketHandler(clientSocket, processor)).start();
            }
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + PORT);
            System.exit(1);
        }
    }

}
