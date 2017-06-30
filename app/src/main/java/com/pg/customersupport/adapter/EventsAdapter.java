package com.pg.customersupport.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pg.customersupport.R;
import com.pg.customersupport.model.ticket.Event;

import java.util.List;

/**
 * The adapter to bind the tickets events view
 * to its data model
 *
 * @author PG
 * @version 1.0
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    /**
     * The adaptor reference for the list of events
     * bound to the Recycler View
     */
    private final List<Event> mEventsList;

    public EventsAdapter(List<Event> eventsList) {
        this.mEventsList = eventsList;
    }

    @Override
    public EventsAdapter.EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View simpleRow = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_events, parent, false);

        return new EventsViewHolder(simpleRow);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.EventsViewHolder holder, int position) {
        final Event event = mEventsList.get(position);
        String meta = event.getAuthor();
        if (event.getCreateAt().length() > 19)
            meta = meta + " (" + event.getCreateAt().substring(0, 10) +
                    " " + event.getCreateAt().substring(11, 16) + ")";

        String body = (event.getBody() != null ? event.getBody().getMessage() : "");
        holder.metaInformation.setText(meta);
        holder.body.setText(body);
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    /**
     * Use to update the Event list within the adaptor
     * and notify the adapter of data set changes
     */
    public void swap(List<Event> eventsList) {
        mEventsList.clear();
        mEventsList.addAll(eventsList);
        notifyDataSetChanged();
    }

    /**
     * The view holder for the RecyclerView
     *
     * @see RecyclerView.ViewHolder
     */
    public static class EventsViewHolder extends RecyclerView.ViewHolder {

        //Views within the view holder
        public final TextView metaInformation, body;

        /**
         * Constructor to initialize and bind view to their resource
         *
         * @param v the view
         */
        public EventsViewHolder(View v) {
            super(v);
            metaInformation = (TextView) v.findViewById(R.id.meta_data);
            body = (TextView) v.findViewById(R.id.body);
        }
    }
}
