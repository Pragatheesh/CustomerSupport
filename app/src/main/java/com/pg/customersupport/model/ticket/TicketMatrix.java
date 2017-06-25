package com.pg.customersupport.model.ticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The hold the meta data of the ticket
 *
 * @author PG
 * @version 1.0
 */
public class TicketMatrix {
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("last_status_changed")
    @Expose
    private String lastStatusChanged;
    @SerializedName("waited_time")
    @Expose
    private Integer waitedTime;
    @SerializedName("worked_time")
    @Expose
    private Integer workedTime;
    @SerializedName("resolution_time")
    @Expose
    private Integer resolutionTime;
    @SerializedName("sla_violated")
    @Expose
    private Boolean slaViolated;
    @SerializedName("reopens")
    @Expose
    private Integer reopens;
    @SerializedName("replies")
    @Expose
    private Integer replies;
    @SerializedName("assignees")
    @Expose
    private Integer assignees;
    @SerializedName("groups")
    @Expose
    private Integer groups;
    @SerializedName("_id")
    @Expose
    private String id;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastStatusChanged() {
        return lastStatusChanged;
    }

    public void setLastStatusChanged(String lastStatusChanged) {
        this.lastStatusChanged = lastStatusChanged;
    }

    public Integer getWaitedTime() {
        return waitedTime;
    }

    public void setWaitedTime(Integer waitedTime) {
        this.waitedTime = waitedTime;
    }

    public Integer getWorkedTime() {
        return workedTime;
    }

    public void setWorkedTime(Integer workedTime) {
        this.workedTime = workedTime;
    }

    public Integer getResolutionTime() {
        return resolutionTime;
    }

    public void setResolutionTime(Integer resolutionTime) {
        this.resolutionTime = resolutionTime;
    }

    public Boolean getSlaViolated() {
        return slaViolated;
    }

    public void setSlaViolated(Boolean slaViolated) {
        this.slaViolated = slaViolated;
    }

    public Integer getReopens() {
        return reopens;
    }

    public void setReopens(Integer reopens) {
        this.reopens = reopens;
    }

    public Integer getReplies() {
        return replies;
    }

    public void setReplies(Integer replies) {
        this.replies = replies;
    }

    public Integer getAssignees() {
        return assignees;
    }

    public void setAssignees(Integer assignees) {
        this.assignees = assignees;
    }

    public Integer getGroups() {
        return groups;
    }

    public void setGroups(Integer groups) {
        this.groups = groups;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TicketMatrix{" +
                "createdAt='" + createdAt + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", lastStatusChanged='" + lastStatusChanged + '\'' +
                ", waitedTime=" + waitedTime +
                ", workedTime=" + workedTime +
                ", resolutionTime=" + resolutionTime +
                ", slaViolated=" + slaViolated +
                ", reopens=" + reopens +
                ", replies=" + replies +
                ", assignees=" + assignees +
                ", groups=" + groups +
                ", id='" + id + '\'' +
                '}';
    }
}
