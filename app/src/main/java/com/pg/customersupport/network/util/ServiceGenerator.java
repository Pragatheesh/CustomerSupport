package com.pg.customersupport.network.util;

import android.text.TextUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Generates the service for defined interface of type on tickets operations.
 * Provides implementations with and without authorization header
 *
 * @author PG
 * @version 1.0
 * @see OkHttpClient
 * @see Retrofit
 */
public class ServiceGenerator {

    private static final String API_BASE_URL = "http://liteticket.app.veery.cloud/DVP/API/1.0.0.0/";

    private static final Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    /**
     * Service generator public interface to create a service class
     * where auth token is not necessary for the api call
     *
     * @param serviceClass the api service class
     * @param <S>          generics to accommodate type safe implementation
     * @return the service to make api calls
     */
    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    /**
     * Service generator public interface to create a service class
     * where auth token is necessary to be appended for the api call
     *
     * @param serviceClass the api service class
     * @param authToken    the auth token that needs to be added
     * @param <S>          generics to accommodate type safe implementation
     * @return the service to make api calls
     */
    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                builder.client(httpClient.build());
            }
        }

        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }
}
