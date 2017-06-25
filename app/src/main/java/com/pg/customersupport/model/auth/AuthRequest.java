package com.pg.customersupport.model.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model to store the Authorization request body
 *
 * @author PG
 * @version 1.0
 * @see com.google.gson.Gson
 */
public class AuthRequest {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("scope")
    @Expose
    private List<String> scope = null;
    @SerializedName("console")
    @Expose
    private String console;
    @SerializedName("clientID")
    @Expose
    private String clientID;

    public AuthRequest(String userName, String password, List<String> scope, String console, String clientID) {
        this.userName = userName;
        this.password = password;
        this.scope = scope;
        this.console = console;
        this.clientID = clientID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

}
