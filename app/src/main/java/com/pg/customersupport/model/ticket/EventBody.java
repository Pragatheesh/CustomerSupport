package com.pg.customersupport.model.ticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The modal for the ticket events body which
 * contains more information on the events
 *
 * @author PG
 * @version 1.0
 */
public class EventBody {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("time")
    @Expose
    private String time;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}