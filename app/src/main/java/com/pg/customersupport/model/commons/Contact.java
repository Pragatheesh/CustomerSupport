package com.pg.customersupport.model.commons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model to hold a contact of a person / place
 *
 * @author PG
 * @version 1.0
 */
public class Contact {
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("verified")
    @Expose
    private Boolean verified;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contact='" + contact + '\'' +
                ", type='" + type + '\'' +
                ", verified=" + verified +
                '}';
    }
}
