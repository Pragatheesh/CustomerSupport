package com.pg.customersupport;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pg.customersupport.model.auth.AuthRequest;
import com.pg.customersupport.model.response.AuthResponse;
import com.pg.customersupport.network.UserApiService;
import com.pg.customersupport.network.util.UserServiceGenerator;
import com.pg.customersupport.util.AppProgressDialog;
import com.pg.customersupport.util.Connectivity;
import com.pg.customersupport.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password for the AGENT_CONSOLE.
 *
 * @author PG
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private UserApiService mUserService;
    private PreferenceManager mPreferenceManager;

    // UI references.
    private EditText mEmailView, mPasswordView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // See if the user is already authenticated, if not
        // present the authentication screen
        mPreferenceManager = new PreferenceManager(getApplicationContext());
        if (mPreferenceManager.isUserAuthenticated()) {
            Intent i = new Intent(LoginActivity.this, MyTicketsActivity.class);
            startActivity(i);
            LoginActivity.this.finish();
        }

        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.email_login_form);
        View mForgotPassword = findViewById(R.id.forgot_password);
        mForgotPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mLoginFormView, getResources().getString(R.string.feature_to_implement), Snackbar.LENGTH_SHORT).show();
            }
        });

        // Initiate the user service for login attempt
        mUserService = UserServiceGenerator.createService(UserApiService.class);
    }


    /**
     * Attempts to sign in to the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel)
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            List<String> scope = new ArrayList<>();
            scope.add(getResources().getString(R.string.scope));

            AuthRequest authRequest = new AuthRequest(email, password, scope, getResources().getString(R.string.console),
                    getResources().getString(R.string.clientID));

            if (Connectivity.isConnected(getApplicationContext()))
                performLogin(authRequest);
            else
                Snackbar.make(mLoginFormView, getResources().getString(R.string.network_error), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void performLogin(AuthRequest authRequest) {

        AppProgressDialog.showProgressDialog(LoginActivity.this,
                getResources().getString(R.string.authenticating));

        Call<AuthResponse> authResponseCall = mUserService.attemptServerLogin(authRequest);
        authResponseCall.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                AppProgressDialog.hideProgressDialog();
                switch (response.code()) {
                    case 200:
                        if (response.body().getState().equals("login")) {
                            mPreferenceManager.storeAuthToken(response.body().getToken());

                            //Start intent to navigate to another activity
                            Intent i = new Intent(LoginActivity.this, MyTicketsActivity.class);
                            startActivity(i);
                            LoginActivity.this.finish();
                        }
                        break;
                    case 401:
                        Snackbar.make(mLoginFormView, getResources().getString(R.string.login_error), Snackbar.LENGTH_SHORT).show();
                        break;
                    default:
                        Snackbar.make(mLoginFormView, getResources().getString(R.string.invalid_network), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.d(TAG, getResources().getString(R.string.invalid_network));
                AppProgressDialog.hideProgressDialog();
            }
        });

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    protected void onPause() {
        AppProgressDialog.hideProgressDialog();
        super.onPause();
    }
}

