package com.pg.customersupport.model.ticket;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Holds the events that are taken place on a Ticket
 *
 * @author PG
 * @version 2.0
 * @see Ticket
 */
public class Event {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("body")
    @Expose
    private EventBody body;
    @SerializedName("create_at")
    @Expose
    private String createAt;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("type")
    @Expose
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventBody getBody() {
        return body;
    }

    public void setBody(EventBody body) {
        this.body = body;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
