/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.client;

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
public class Connection implements Runnable {

    protected BufferedInputStream in;
    protected BufferedOutputStream out;
    protected ObjectOutputStream ous;
    protected ObjectInputStream ois;
    protected Socket clientSocket;

    protected String host;
    protected String name;
    protected int port;

    public Connection(String host, int port, String name) {
        this.host = host;
        this.name = name;
        this.port = port;
    }

    @Override
    public void run() {

        try {
            clientSocket = new Socket(host, port);
            in = new BufferedInputStream(clientSocket.getInputStream());
            out = new BufferedOutputStream(clientSocket.getOutputStream());

        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void login(ResultCallback cb, ResultCallback error) {
        try {
            ous = new ObjectOutputStream(out);
            ous.writeObject(new Command(ServerCommands.Login, name));
            ous.flush();

            Result res;
            try {
                ois = new ObjectInputStream(in);
                res = (Result) ois.readObject();
                if (!res.isError()) {
                    cb.invoke(res);
                } else {
                    error.invoke(res);
                }
            } catch (ClassNotFoundException ex) {
                error.invoke(new Result("ClassNotFoundException", ex.getMessage(), true));
            }
        } catch (IOException ex) {
            error.invoke(new Result("IOException", ex.getMessage(), true));
        }
    }

    public void execute(ServerCommands command, String data, ResultCallback cb) {
        execute(command, data, cb, null);
    }

    public void execute(ServerCommands command, String data, ResultCallback cb, ResultCallback error) {
        try {
            ous.writeObject(new Command(command, data));
            ous.flush();
            Result res;
            try {
                res = (Result) ois.readObject();
                if (!res.isError()) {
                    cb.invoke(res);
                } else if (error != null) {
                    error.invoke(res);
                }
            } catch (ClassNotFoundException ex) {
                if (error != null) {
                    error.invoke(new Result("ClassNotFoundException", ex.getMessage(), true));
                }
            }
        } catch (IOException ex) {
            if (error != null) {
                error.invoke(new Result("IOException", ex.getMessage(), true));
            }
        }
    }
}
