package com.pg.customersupport.model.ticket;

/**
 * The priority level of the tickets that a user handle with
 *
 * @author PG
 * @version 1.0
 */
public enum TicketPriority {
    /**
     * Needs to be resolved immediately
     */
    URGENT,

    /**
     * Solved quickly within the agreen SLA
     */
    HIGH,

    /**
     * The agent can maintain the normal SLA standard
     */
    NORMAL,

    /**
     * Not impacting major, hence time can be taken to resolve issue
     */
    LOW
}
