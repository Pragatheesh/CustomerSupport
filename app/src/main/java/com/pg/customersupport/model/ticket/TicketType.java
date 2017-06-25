package com.pg.customersupport.model.ticket;

/**
 * The type of the tickets that a user handle with
 *
 * @author PG
 * @version 1.0
 */
public enum TicketType {
    /**
     * The customer has requested the agent has to
     * perform some action on this ticket
     */
    ACTION,

    /**
     * Customer has requested for certain information
     */
    INQUERY,

    /**
     * Customer has posted a question which is to be responded
     */
    QUESTION,

    /**
     * The customer has made a complain and needs to be
     * resolved by the agent
     */
    COMPLAIN
}
