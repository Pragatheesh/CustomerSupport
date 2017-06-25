package com.pg.customersupport;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pg.customersupport.fragment.MyTicketsCommunicator;
import com.pg.customersupport.fragment.MyTicketsCompleted;
import com.pg.customersupport.fragment.MyTicketsNew;
import com.pg.customersupport.model.ticket.TicketStatus;
import com.pg.customersupport.util.PreferenceManager;

/**
 * The parent activity for controlling the fragments on my issues
 *
 * @author PG
 * @version 1.0
 */
public class MyTicketsActivity extends AppCompatActivity implements MyTicketsCommunicator {

    /*
        The {@link ViewPager} that will host the section contents.
    */
    private ViewPager mViewPager;

    private MyTicketsNew mNewTickectsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
            The {@link android.support.v4.view.PagerAdapter} that will provide
            fragments for each of the sections. We use a
            {@link FragmentPagerAdapter} derivative, which will keep every
            loaded fragment in memory. If this becomes too memory intensive, it
            may be best to switch to a
            {@link android.support.v4.app.FragmentStatePagerAdapter}.
        */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void performLogout() {
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        preferenceManager.removeAuthToken();
        preferenceManager.clearAll();

        Intent i = new Intent(MyTicketsActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        MyTicketsActivity.this.finish();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Returning the current tab
            switch (position) {
                case 0:
                    return mNewTickectsFragment = new MyTicketsNew();
                case 1:
                    return new MyTicketsCompleted();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return TicketStatus.NEW.toString();
                case 1:
                    return TicketStatus.COMPLETED.toString();
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_tickets, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        switch (item.getItemId()) {
            case R.id.action_search:
                Snackbar.make(mViewPager, getResources().getString(R.string.search_break), Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.action_refresh:
                if (mNewTickectsFragment != null)
                    mNewTickectsFragment.getMyTickets();
                break;
            case R.id.action_filter:
                Snackbar.make(mViewPager, getResources().getString(R.string.filter_break), Snackbar.LENGTH_LONG).show();
                break;
            case R.id.action_profile:
                Intent intent = new Intent(MyTicketsActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.action_logout:
                performLogout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
