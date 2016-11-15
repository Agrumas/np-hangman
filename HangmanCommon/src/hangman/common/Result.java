/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.common;

import java.io.Serializable;

/**
 *
 * @author Algirdas
 */
public class Result implements Serializable {
    public String type;
    public String data;
    public boolean error;

    public Result(String type) {
        this(type, "", false);
    }
    
    
    public Result(String type, boolean err) {
        this(type, "", err);
    }
   
    public Result(String type, String data, boolean err) {
        this.type = type;
        this.data = data;
        this.error = err;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public boolean isError() {
        return error;
    }
    

    @Override
    public String toString() {
        return "Result{" + "type=" + type + ", data=" + data + '}';
    }
    
}
