package com.pg.customersupport.model.ticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The model of a ticket with all of its information
 *
 * @author PG
 * @version 1.0
 * @see TicketMatrix
 * @see Submitter
 * @see Requester
 */
public class Ticket {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("ticket_matrix")
    @Expose
    private TicketMatrix ticketMatrix;
    @SerializedName("requester")
    @Expose
    private Requester requester;
    @SerializedName("tid")
    @Expose
    private Integer tid;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("submitter")
    @Expose
    private Submitter submitter;
    @SerializedName("time_estimation")
    @Expose
    private Integer timeEstimation;
    @SerializedName("comments")
    @Expose
    private List<Object> comments = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TicketMatrix getTicketMatrix() {
        return ticketMatrix;
    }

    public void setTicketMatrix(TicketMatrix ticketMatrix) {
        this.ticketMatrix = ticketMatrix;
    }

    public Requester getRequester() {
        return requester;
    }

    public void setRequester(Requester requester) {
        this.requester = requester;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Submitter getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Submitter submitter) {
        this.submitter = submitter;
    }

    public Integer getTimeEstimation() {
        return timeEstimation;
    }

    public void setTimeEstimation(Integer timeEstimation) {
        this.timeEstimation = timeEstimation;
    }

    public List<Object> getComments() {
        return comments;
    }

    public void setComments(List<Object> comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", ticketMatrix=" + ticketMatrix +
                ", requester=" + requester +
                ", tid=" + tid +
                ", active=" + active +
                ", type='" + type + '\'' +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", submitter=" + submitter +
                ", timeEstimation=" + timeEstimation +
                ", comments=" + comments +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
