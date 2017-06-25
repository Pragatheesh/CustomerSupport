package com.pg.customersupport.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pg.customersupport.model.ticket.Ticket;

import java.util.List;

/**
 * The response model to hold the list of tickets
 *
 * @author PG
 * @version 1.0
 * @see Ticket
 */
public class TicketsResponse {

    @SerializedName("Exception")
    @Expose
    private Object exception;
    @SerializedName("CustomMessage")
    @Expose
    private String customMessage;
    @SerializedName("IsSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("Result")
    @Expose
    private List<Ticket> ticket = null;

    public Object getException() {
        return exception;
    }

    public void setException(Object exception) {
        this.exception = exception;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public List<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "TicketsResponse{" +
                "exception=" + exception +
                ", customMessage='" + customMessage + '\'' +
                ", isSuccess=" + isSuccess +
                ", ticket=" + ticket +
                '}';
    }
}