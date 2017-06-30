package com.pg.customersupport;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pg.customersupport.adapter.EventsAdapter;
import com.pg.customersupport.model.commons.Contact;
import com.pg.customersupport.model.response.TicketResponse;
import com.pg.customersupport.model.ticket.Event;
import com.pg.customersupport.model.ticket.Ticket;
import com.pg.customersupport.model.ticket.TicketPriority;
import com.pg.customersupport.model.ticket.TicketType;
import com.pg.customersupport.network.TicketApiService;
import com.pg.customersupport.network.util.ServiceGenerator;
import com.pg.customersupport.util.AppProgressDialog;
import com.pg.customersupport.util.Connectivity;
import com.pg.customersupport.util.PicassoCircularTransform;
import com.pg.customersupport.util.PreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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

    private Ticket mTicket;

    private ImageView mType, mSLA, mPriority, mRequesterAvatar;
    private TextView mSubject, mReference, mETA, mWorkLogged, mDescription, mRequesterName;

    private LinearLayout mContactsHolder;

    private EventsAdapter mAdapter;
    private List<Event> mEventsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);

        //initialize UI components
        mType = (ImageView) findViewById(R.id.ticket_type);
        mSLA = (ImageView) findViewById(R.id.ticket_sla);
        mPriority = (ImageView) findViewById(R.id.ticket_priority);
        mRequesterAvatar = (ImageView) findViewById(R.id.ticket_requester_avatar);

        mSubject = (TextView) findViewById(R.id.ticket_subject);
        mReference = (TextView) findViewById(R.id.ticket_reference);
        mETA = (TextView) findViewById(R.id.ticket_eta);
        mWorkLogged = (TextView) findViewById(R.id.ticket_work);
        mDescription = (TextView) findViewById(R.id.ticket_description);
        mRequesterName = (TextView) findViewById(R.id.ticket_requester);

        mContactsHolder = (LinearLayout) findViewById(R.id.requester_contacts);

        //Initialize the adapter
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.activity_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mAdapter = new EventsAdapter(mEventsList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        if (getIntent().getExtras() != null) {
            String ticketId = getIntent().getExtras().getString("TICKET_ID");
            getTicketDetails(ticketId);
        } else
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.token_id_error), Toast.LENGTH_SHORT).show();
    }

    /*
        Method to draw the ticket information on the UI
     */
    private void drawUI() {
        drawTicketMetaInfo();
        if (mTicket.getRequester().getContacts() != null)
            drawRequesterContacts();

        mEventsList = mTicket.getEvents();
        mAdapter.swap(mEventsList);
    }

    /*
        Draws on the UI the basic information of the ticket
        Type, Subject, SLA, Priority, Description, Time logs, reference
     */
    @SuppressLint("SetTextI18n")
    private void drawTicketMetaInfo() {
        //Set Text Information
        mSubject.setText(mTicket.getSubject() == null ? getString(R.string.not_specified) : mTicket.getSubject());
        mReference.setText(mTicket.getReference() == null ? getString(R.string.not_specified) : mTicket.getReference());

        mETA.setText("ETA:  " + (mTicket.getTimeEstimation() == null
                ? "0" : mTicket.getTimeEstimation() / 3600) + " h");
        mWorkLogged.setText(mTicket.getTicketMatrix().getWorkedTime() / 60 + " mins");

        mDescription.setText(mTicket.getDescription() == null ? getString(R.string.not_specified) : mTicket.getDescription());

        String requesterFullName = mTicket.getRequester() == null ? getString(R.string.not_specified)
                : mTicket.getRequester().getFullName();
        mRequesterName.setText(requesterFullName);

        //Set Graphical Information
        if (mTicket.getType().equalsIgnoreCase(TicketType.ACTION.toString())) {
            mType.setImageResource(R.drawable.ic_action_black_24dp);
            mType.setColorFilter(Color.rgb(52, 120, 229));
        } else if (mTicket.getType().equalsIgnoreCase(TicketType.INQUERY.toString())) {
            mType.setImageResource(R.drawable.ic_inquiry_black_24dp);
            mType.setColorFilter(Color.rgb(249, 168, 37));
        } else if (mTicket.getType().equalsIgnoreCase(TicketType.COMPLAIN.toString())) {
            mType.setImageResource(R.drawable.ic_complain_24dp);
            mType.setColorFilter(Color.rgb(213, 0, 0));
        } else if (mTicket.getType().equalsIgnoreCase(TicketType.QUESTION.toString())) {
            mType.setImageResource(R.drawable.ic_inquiry_black_24dp);
            mType.setColorFilter(Color.rgb(52, 120, 229));
        }

        mPriority.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
        if (mTicket.getPriority().equalsIgnoreCase(TicketPriority.URGENT.toString()))
            mPriority.setColorFilter(Color.rgb(213, 0, 0));
        else if (mTicket.getPriority().equalsIgnoreCase(TicketPriority.HIGH.toString()))
            mPriority.setColorFilter(Color.rgb(249, 168, 37));
        else if (mTicket.getPriority().equalsIgnoreCase(TicketPriority.NORMAL.toString()))
            mPriority.setColorFilter(Color.rgb(52, 120, 229));
        else if (mTicket.getPriority().equalsIgnoreCase(TicketPriority.LOW.toString())) {
            mPriority.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
            mPriority.setColorFilter(Color.rgb(0, 200, 83));
        }

        if (mTicket.getTicketMatrix() != null && mTicket.getTicketMatrix().getSlaViolated())
            mSLA.setVisibility(View.VISIBLE);

        if (mTicket.getRequester() != null && !mTicket.getRequester().getAvatar().equals(""))
            Picasso.with(getApplicationContext()).load(mTicket.getRequester().getAvatar()).resize(120, 120)
                    .transform(new PicassoCircularTransform()).into(mRequesterAvatar);
    }

    private void drawRequesterContacts() {
        List<Contact> contacts = mTicket.getRequester().getContacts();
        LinearLayout[] llx = new LinearLayout[contacts.size()];
        TextView[] tvInformation = new TextView[contacts.size()];
        ImageView[] ivIcon = new ImageView[contacts.size()];

        for (int i = 0; i < contacts.size(); i++) {
            llx[i] = new LinearLayout(this);
            ivIcon[i] = new ImageView(this);
            tvInformation[i] = new TextView(this);

            ivIcon[i].setLayoutParams(new LinearLayout.LayoutParams(0,
                    LayoutParams.WRAP_CONTENT, 0.1f));
            tvInformation[i].setLayoutParams(new LinearLayout.LayoutParams(0,
                    LayoutParams.WRAP_CONTENT, 0.9f));

            tvInformation[i].setText(contacts.get(i).getContact() == null ? getString(R.string.not_specified) : contacts.get(i).getContact());

            ivIcon[i].setColorFilter(Color.rgb(144, 144, 144));
            if (contacts.get(i).getType().equals("phone")) {
                ivIcon[i].setImageResource(R.drawable.ic_phone_iphone_black_24dp);
            } else {
                ivIcon[i].setImageResource(R.drawable.ic_email_black_24dp);
            }

            ivIcon[i].setTag(i);
            tvInformation[i].setTag(i);
            llx[i].addView(ivIcon[i]);
            llx[i].addView(tvInformation[i]);
            mContactsHolder.addView(llx[i]);
        }
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
                                mTicket = response.body().getTicket();
                                if (mTicket != null)
                                    drawUI();
                                else
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.ticket_not_found), Toast.LENGTH_SHORT).show();
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
