package com.pg.customersupport.network;

import com.pg.customersupport.model.response.TicketResponse;
import com.pg.customersupport.model.response.TicketsResponse;
import com.pg.customersupport.model.ticket.Ticket;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The api definitions for the Ticket related requests
 *
 * @author PG
 * @version 1.0
 * @see retrofit2.Retrofit
 */
public interface TicketApiService {

    /**
     * Get all tickets of the logged in user using his/her credentials
     *
     * @param limit  the number of tickets to be responded on a page
     * @param page   the current page to be retrieved
     * @param status the ticket status be filtered on the request
     * @return the response with the list of tickets for the logged in user
     * @see TicketsResponse
     * @see Ticket
     */
    @GET("MyTickets/{limit}/{page}")
    Call<TicketsResponse> getMyTickets(@Path("limit") int limit, @Path("page") int page,
                                       @Query("status") String status);

    /**
     * Service method to get details of one single ticket
     *
     * @param id the id of the ticket to be retrieved
     * @return the response which holds details of the ticket
     */
    @GET("Ticket/{id}/Details")
    Call<TicketResponse> getOneTicket(@Path("id") String id);
}
