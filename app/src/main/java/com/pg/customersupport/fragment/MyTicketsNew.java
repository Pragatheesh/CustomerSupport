package com.pg.customersupport.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pg.customersupport.R;
import com.pg.customersupport.adapter.TicketsAdapter;
import com.pg.customersupport.model.response.TicketsResponse;
import com.pg.customersupport.model.ticket.Ticket;
import com.pg.customersupport.network.TicketApiService;
import com.pg.customersupport.network.util.ServiceGenerator;
import com.pg.customersupport.util.Connectivity;
import com.pg.customersupport.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment to display the new active issues
 * that are assigned for the logged in user
 *
 * @author PG
 * @version 1.0
 */
public class MyTicketsNew extends Fragment {

    private static final String TAG = "MyTicketsNewFragment";

    /**
     * MyTicketsCommunicator interface reference variable
     * to dynamically invoke the activity in relation
     *
     * @see MyTicketsCommunicator
     */
    private MyTicketsCommunicator mCom;

    private TicketsAdapter mAdapter;
    private List<Ticket> mTicketsList = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
          Reference the activity invoking the fragment
          to pass back interaction details via the interface methods
         */
        mCom = (MyTicketsCommunicator) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_tickets_new, container, false);

        //retrieve data from the server
        getMyTickets();

        //Initialize views
        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.tickets_list);

        //Set up the recycler view
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mAdapter = new TicketsAdapter(getActivity().getApplicationContext(), mTicketsList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }

    /**
     * Perform network call to get all the logged in users tickets
     */
    public void getMyTickets() {
        if (Connectivity.isConnected(getActivity().getApplicationContext())) {
            PreferenceManager preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
            TicketApiService mTicketService = ServiceGenerator.createService(TicketApiService.class, preferenceManager.getAccessToken());

            Call<TicketsResponse> ticketsResponseCall = mTicketService.getMyTickets(10, 1, "new");
            ticketsResponseCall.enqueue(new Callback<TicketsResponse>() {
                @Override
                public void onResponse(Call<TicketsResponse> call, Response<TicketsResponse> response) {
                    switch (response.code()) {
                        case 200:
                            if (response.body().getIsSuccess()) {
                                mTicketsList = response.body().getTicket();
                                mAdapter.swap(mTicketsList);
                            } else
                                Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.tickets_request_failure),
                                        Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            mCom.performLogout();
                    }
                }

                @Override
                public void onFailure(Call<TicketsResponse> call, Throwable t) {
                    Log.d(TAG, getResources().getString(R.string.invalid_network));
                }
            });
        } else
            Toast.makeText(getActivity().getApplicationContext(),
                    getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }
}