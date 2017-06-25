package com.pg.customersupport.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pg.customersupport.R;

/**
 * The fragment to handle the completed issues of the
 * logged in user. As of implementation, this class does not
 * do much, it just shows some static text.
 *
 * @author PG
 * @version 1.0
 */
public class MyTicketsCompleted extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_tickets_completed, container, false);
    }
}
