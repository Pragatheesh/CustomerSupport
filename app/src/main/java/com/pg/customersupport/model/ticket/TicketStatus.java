package com.pg.customersupport.model.ticket;

/**
 * The status of the tickets that a user handle with
 *
 * @author PG
 * @version 1.0
 */
public enum TicketStatus {
    /**
     * Tickets that are yet to be handled by the agent
     */
    NEW,

    /**
     * The tickets that an action is taken and the issue is resolved
     */
    COMPLETED
}
