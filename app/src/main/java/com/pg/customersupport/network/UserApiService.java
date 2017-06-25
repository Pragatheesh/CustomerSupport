package com.pg.customersupport.network;

import com.pg.customersupport.model.auth.AuthRequest;
import com.pg.customersupport.model.response.AuthResponse;
import com.pg.customersupport.model.response.UserProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * The api definitions for the User related calls
 *
 * @author PG
 * @version 1.0
 * @see retrofit2.Retrofit
 */
public interface UserApiService {

    /**
     * Attempt login with the user credentials. On success,
     * responded with the auth_token
     *
     * @param authRequest The request object with the user credentials
     * @return the response from server containing auth_token and messages
     */
    @POST("auth/login")
    Call<AuthResponse> attemptServerLogin(@Body AuthRequest authRequest);

    /**
     * Get the logged in user profile with the user credentials
     *
     * @return the response containing the user details
     */
    @GET("DVP/API/1.0.0.0/Myprofile")
    Call<UserProfileResponse> getUserProfile();
}
