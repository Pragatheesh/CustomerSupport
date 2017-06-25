package com.pg.customersupport.fragment;

/**
 * Fragment Communicator pattern implementation.
 * interface to decoupled interactions on MyIssuesActivity and fragment
 *
 * @author PG
 * @version 1.0
 */
public interface MyTicketsCommunicator {

    /**
     * Invoked when the user wants to logout or in a
     * scenario when the user token is no more valid
     */
    void performLogout();
}
