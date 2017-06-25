package com.pg.customersupport.network.util;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor on all network requests to add a Authorization
 * header where a auth_token is supplied for the request
 *
 * @author PG
 * @version 1.0
 * @see Interceptor
 */
class AuthenticationInterceptor implements Interceptor {

    private final String mAuthToken;

    /**
     * Constructor for the auth interceptor
     *
     * @param token The auth token that needs to be added to headers of network requests
     */
    public AuthenticationInterceptor(String token) {
        this.mAuthToken = token;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", "Bearer " + mAuthToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}

