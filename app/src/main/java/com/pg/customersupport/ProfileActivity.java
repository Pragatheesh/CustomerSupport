package com.pg.customersupport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pg.customersupport.model.response.UserProfileResponse;
import com.pg.customersupport.model.user.User;
import com.pg.customersupport.network.UserApiService;
import com.pg.customersupport.network.util.UserServiceGenerator;
import com.pg.customersupport.util.AppProgressDialog;
import com.pg.customersupport.util.Connectivity;
import com.pg.customersupport.util.PicassoCircularTransform;
import com.pg.customersupport.util.PreferenceManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The activity presenting the logged in users profile
 *
 * @author PG
 * @version 1.0
 */
public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private UserApiService mUserService;
    private User mUser;

    // View components
    private ImageView mAvatar, mEmailVerify, mPhoneVerify;
    private TextView mName, mEmailInfo, mBirthday, mGender, mAddress, mPhone, mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        mUserService = UserServiceGenerator.createService(UserApiService.class,
                preferenceManager.getAccessToken());

        // Initialize UI
        mAvatar = (ImageView) findViewById(R.id.user_avatar);
        mEmailVerify = (ImageView) findViewById(R.id.image_email_verified);
        mPhoneVerify = (ImageView) findViewById(R.id.image_phone_verified);

        mName = (TextView) findViewById(R.id.user_full_name);
        mEmailInfo = (TextView) findViewById(R.id.user_email);
        mBirthday = (TextView) findViewById(R.id.birthday);
        mGender = (TextView) findViewById(R.id.gender);
        mAddress = (TextView) findViewById(R.id.address);
        mPhone = (TextView) findViewById(R.id.phone);
        mEmail = (TextView) findViewById(R.id.email);

        getUserProfile();
    }

    /**
     * Draw the user info on the UI elements once after the necessary information is loaded
     */
    private void drawUserUI() {
        if (mUser.getAvatar() != null && !mUser.getAvatar().equals(""))
            Picasso.with(getApplicationContext()).load(mUser.getAvatar()).resize(200, 200)
                    .transform(new PicassoCircularTransform()).into(mAvatar);
        String fullName = mUser.getTitle() + ". " + mUser.getFirstname() + " " + mUser.getLastname();

        mName.setText(fullName);
        mBirthday.setText(mUser.getBirthday().substring(0, 10));
        mGender.setText(mUser.getGender().toUpperCase());
        mAddress.setText(mUser.getAddress().getReadableAddress());
        mPhone.setText(mUser.getPhoneNumber().getContact());
        mEmailInfo.setText(mUser.getEmail().getContact());
        mEmail.setText(mUser.getEmail().getContact());

        if (mUser.getPhoneNumber().getVerified())
            mPhoneVerify.setVisibility(View.VISIBLE);

        if (mUser.getEmail().getVerified())
            mEmailVerify.setVisibility(View.VISIBLE);
    }

    /**
     * Network call to retrieve the logged in user information
     */
    private void getUserProfile() {
        if (Connectivity.isConnected(getApplicationContext())) {
            AppProgressDialog.showProgressDialog(ProfileActivity.this,
                    getResources().getString(R.string.retrieving_profile));

            Call<UserProfileResponse> userProfileResponseCall = mUserService.getUserProfile();
            userProfileResponseCall.enqueue(new Callback<UserProfileResponse>() {
                @Override
                public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                    AppProgressDialog.hideProgressDialog();

                    switch (response.code()) {
                        case 200:
                            if (response.body().getSuccess()) {
                                mUser = response.body().getUser();
                                drawUserUI();
                            }
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_network), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                    Log.d(TAG, getResources().getString(R.string.invalid_network));
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
