/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.client;

import hangman.common.ResultCallback;
import hangman.common.Command;
import hangman.common.Result;
import hangman.common.ServerCommands;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;
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
    protected boolean connected = false;
    protected ResultCallback connectCb;
    protected ResultCallback error;

    protected HashMap<UUID, Command> waitingQueue;

    public Connection() {
        this.waitingQueue = new HashMap<>();
    }

    public void setConnection(String host, int port, String name) {
        this.host = host;
        this.name = name;
        this.port = port;
    }

    public void setConnectionCb(ResultCallback connectCb, ResultCallback error) {
        this.connectCb = connectCb;
        this.error = error;
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket(host, port);
            init(connectCb, error);
            Result res;
            Command cmd;
            while (!clientSocket.isClosed()) {
                try {
                    res = (Result) ois.readObject();
                    cmd = waitingQueue.remove(res.inReply);
                    if (cmd != null) {
                        cmd.dispatch(res);
                    }
                } catch (ClassNotFoundException ex) {
                    if (error != null) {
                        error.invoke(new Result("ClassNotFoundException", ex.getMessage(), true));
                    }
                }
            }
        } catch (IOException ex) {
            if (error != null) {
                error.invoke(new Result("IOException", ex.getMessage(), true));
            }
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void init(ResultCallback cb, ResultCallback error) {
        try {
            in = new BufferedInputStream(clientSocket.getInputStream());
            out = new BufferedOutputStream(clientSocket.getOutputStream());
            System.out.println("Connecting..");
            ous = new ObjectOutputStream(out);
            ous.writeObject(new Command(ServerCommands.Login, name));
            ous.flush();
            ois = new ObjectInputStream(in);

            Result res;
            try {
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

    public void execute(ServerCommands command, ResultCallback cb) {
        execute(command, "", cb, null);
    }

    public void execute(ServerCommands command, ResultCallback cb, ResultCallback error) {
        execute(command, "", cb, error);
    }

    public void execute(ServerCommands command, String data, ResultCallback cb) {
        execute(command, data, cb, null);
    }

    public void execute(ServerCommands command, String data, ResultCallback cb, ResultCallback error) {
        Command cmd = new Command(command, data);
        cmd.setCallbacks(cb, error);
        waitingQueue.put(cmd.id, cmd);
        try {
            ous.writeObject(cmd);
            ous.flush();
        } catch (IOException ex) {
            if (error != null) {
                error.invoke(new Result("IOException", ex.getMessage(), true));
            }
        }
    }
}
