/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.common;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Algirdas
 */
public class Command implements Serializable { 

    public ServerCommands name;
    public String data;
    public UUID id;

    private transient ResultCallback onSuccess;
    private transient ResultCallback onError;

    public Command(ServerCommands name) {
        this(name, "");
    }

    /**
     * Command consists of:
     * @param name its like type
     * @param data data which we want to send
     */
    public Command(ServerCommands name, String data) {
        this.onSuccess = null;
        this.name = name;
        this.data = data;
        this.id = UUID.randomUUID();
        this.onSuccess = null;
        this.onError = null;
    }

    @Override
    public String toString() {
        return "Command{" + "name=" + name + ", data=" + data + '}';
    }

    public void dispatch(Result result) {
        if (result.isError()) {
            if (onError != null) {
                onError.invoke(result);
            }
        } else if (onSuccess != null) {
            onSuccess.invoke(result);
        }
    }

    public void setCallbacks(ResultCallback onSuccess, ResultCallback onError) {
        this.onSuccess = onSuccess;
        this.onError = onError;
    }
    
    

    public Result error(String type, String data) {
        return result(type, data, true).setInReply(id);
    }

    public Result result(String type) {
        return result(type, "", false).setInReply(id);
    }

    public Result result(String type, String data) {
        return result(type, data, false).setInReply(id);
    }

    public Result result(String type, String data, boolean error) {
        return new Result(type, data, error).setInReply(id);
    }

}
