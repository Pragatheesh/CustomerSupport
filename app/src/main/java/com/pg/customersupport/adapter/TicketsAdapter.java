package com.pg.customersupport.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pg.customersupport.R;
import com.pg.customersupport.TicketDetails;
import com.pg.customersupport.model.ticket.Ticket;
import com.pg.customersupport.model.ticket.TicketPriority;
import com.pg.customersupport.model.ticket.TicketType;
import com.pg.customersupport.util.PicassoCircularTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Recycler View Adapter for the to bridge the Tickets List View
 * with the relevant data to be bound
 *
 * @author PG
 * @version 1.0
 * @see RecyclerView.Adapter
 */
public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder> {

    /**
     * The adaptor reference for the list of Tickets
     * bound to the Recycler View
     */
    private final List<Ticket> mTicketsList;
    private final Context mContext;

    public TicketsAdapter(Context context, List<Ticket> ticketsList) {
        this.mContext = context;
        this.mTicketsList = ticketsList;
    }

    @Override
    public TicketsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View simpleRow = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_tickets, parent, false);

        return new TicketsViewHolder(simpleRow);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(TicketsViewHolder holder, int position) {

        final Ticket ticket = mTicketsList.get(position);

        // Bind text view data
        holder.subject.setText(ticket.getSubject());

        holder.eta.setText("ETA:  " + (ticket.getTimeEstimation() == null
                ? "0" : ticket.getTimeEstimation() / 3600) + " h");
        holder.workLogged.setText(ticket.getTicketMatrix().getWorkedTime() / 60 + " mins");
        holder.commentCount.setText((ticket.getComments() == null || ticket.getComments().isEmpty())
                ? "0" : String.valueOf(ticket.getComments().size()));
        holder.requester.setText(ticket.getRequester() == null ? "Not Specified" : ticket.getRequester().getName());
        holder.submitter.setText(ticket.getSubmitter() == null ? "Not Specified" :
                (ticket.getSubmitter().getFirstname() + " " + ticket.getSubmitter().getLastname()));

        // Bind image view data
        if (ticket.getType().equalsIgnoreCase(TicketType.ACTION.toString())) {
            holder.type.setImageResource(R.drawable.ic_action_black_24dp);
            holder.type.setColorFilter(Color.rgb(52, 120, 229));
        } else if (ticket.getType().equalsIgnoreCase(TicketType.INQUERY.toString())) {
            holder.type.setImageResource(R.drawable.ic_inquiry_black_24dp);
            holder.type.setColorFilter(Color.rgb(249, 168, 37));
        } else if (ticket.getType().equalsIgnoreCase(TicketType.COMPLAIN.toString())) {
            holder.type.setImageResource(R.drawable.ic_complain_24dp);
            holder.type.setColorFilter(Color.rgb(213, 0, 0));
        } else if (ticket.getType().equalsIgnoreCase(TicketType.QUESTION.toString())) {
            holder.type.setImageResource(R.drawable.ic_inquiry_black_24dp);
            holder.type.setColorFilter(Color.rgb(52, 120, 229));
        }

        holder.priority.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
        if (ticket.getPriority().equalsIgnoreCase(TicketPriority.URGENT.toString()))
            holder.priority.setColorFilter(Color.rgb(213, 0, 0));
        else if (ticket.getPriority().equalsIgnoreCase(TicketPriority.HIGH.toString()))
            holder.priority.setColorFilter(Color.rgb(249, 168, 37));
        else if (ticket.getPriority().equalsIgnoreCase(TicketPriority.NORMAL.toString()))
            holder.priority.setColorFilter(Color.rgb(52, 120, 229));
        else if (ticket.getPriority().equalsIgnoreCase(TicketPriority.LOW.toString())) {
            holder.priority.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
            holder.priority.setColorFilter(Color.rgb(0, 200, 83));
        }

        if (ticket.getTicketMatrix() != null && ticket.getTicketMatrix().getSlaViolated())
            holder.sla.setVisibility(View.VISIBLE);

        // Bind the image of the requester and the submitter
        if (ticket.getRequester() != null && !ticket.getRequester().getAvatar().equals(""))
            Picasso.with(mContext).load(ticket.getRequester().getAvatar()).resize(120, 120)
                    .transform(new PicassoCircularTransform()).into(holder.requesterAvatar);

        //Set click listener for the card view
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, TicketDetails.class);
                i.putExtra("TICKET_ID", ticket.getId());
                i.setFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTicketsList.size();
    }

    /**
     * Use to update the Ticket list within the adaptor
     * and notify the adapter of data set changes
     */
    public void swap(List<Ticket> ticketsList) {
        mTicketsList.clear();
        mTicketsList.addAll(ticketsList);
        notifyDataSetChanged();
    }

    /**
     * The view holder for the RecyclerView
     *
     * @see RecyclerView.ViewHolder
     */
    public static class TicketsViewHolder extends RecyclerView.ViewHolder {

        //Views within the view holder
        public final CardView container;
        public final TextView subject, eta, workLogged, commentCount, requester, submitter;
        public final ImageView type, sla, priority, requesterAvatar;

        /**
         * Constructor to initialize and bind view to their resource
         *
         * @param v the view
         */
        public TicketsViewHolder(View v) {
            super(v);
            container = (CardView) v.findViewById(R.id.card_view);

            subject = (TextView) v.findViewById(R.id.ticket_subject);
            eta = (TextView) v.findViewById(R.id.ticket_eta);
            workLogged = (TextView) v.findViewById(R.id.ticket_work);
            commentCount = (TextView) v.findViewById(R.id.ticket_comment);
            requester = (TextView) v.findViewById(R.id.ticket_requester);
            submitter = (TextView) v.findViewById(R.id.ticket_submitter);

            type = (ImageView) v.findViewById(R.id.ticket_type);
            sla = (ImageView) v.findViewById(R.id.ticket_sla);
            priority = (ImageView) v.findViewById(R.id.ticket_priority);
            requesterAvatar = (ImageView) v.findViewById(R.id.ticket_requester_avatar);
        }
    }
}
