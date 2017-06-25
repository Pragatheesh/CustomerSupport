package com.pg.customersupport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.pg.customersupport.model.response.TicketResponse;
import com.pg.customersupport.network.TicketApiService;
import com.pg.customersupport.network.util.ServiceGenerator;
import com.pg.customersupport.util.AppProgressDialog;
import com.pg.customersupport.util.Connectivity;
import com.pg.customersupport.util.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * View to get the individual detail of a single ticket
 * and present it to the user
 * <p>
 * IMPORTANT: JUST USED FOR DISPLAY PURPOSE AS DID NOT HAVE
 * TIME TO IMPLEMENT THIS FULLY
 *
 * @author PG
 * @version 1.0
 */
public class TicketDetails extends AppCompatActivity {

    private TextView mResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);

        TextView mTicketId = (TextView) findViewById(R.id.ticket_id);
        mResponse = (TextView) findViewById(R.id.response);

        mResponse.setMovementMethod(new ScrollingMovementMethod());

        if (getIntent().getExtras() != null) {
            String ticketId = getIntent().getExtras().getString("TICKET_ID");
            mTicketId.setText(ticketId);
            getTicketDetails(ticketId);
        } else
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.token_id_error), Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to perfrom the network call and retrieve the detail
     * of a single ticket
     *
     * @param ticketId the id of the ticket
     */
    private void getTicketDetails(String ticketId) {
        if (Connectivity.isConnected(getApplicationContext())) {
            AppProgressDialog.showProgressDialog(TicketDetails.this,
                    getResources().getString(R.string.retrieving_information));

            PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
            TicketApiService ticketService = ServiceGenerator.createService(TicketApiService.class,
                    preferenceManager.getAccessToken());

            Call<TicketResponse> ticketResponseCall = ticketService.getOneTicket(ticketId);
            ticketResponseCall.enqueue(new Callback<TicketResponse>() {
                @Override
                public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                    AppProgressDialog.hideProgressDialog();

                    switch (response.code()) {
                        case 200:
                            if (response.body().getSuccess()) {
                                mResponse.setText(response.body().getTicket().toString());
                            } else {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.ticket_not_found), Toast.LENGTH_SHORT).show();
                            }
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_network), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TicketResponse> call, Throwable t) {
                    AppProgressDialog.hideProgressDialog();
                }
            });
        } else
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        AppProgressDialog.hideProgressDialog();
        super.onPause();
    }
}
