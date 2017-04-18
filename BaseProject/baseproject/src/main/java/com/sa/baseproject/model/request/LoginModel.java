package com.sa.baseproject.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mavya.soni on 03/04/17.
 */

public class LoginModel {

    @SerializedName("username")
    private String userName;
    @SerializedName("password")
    private String password;
    @SerializedName("grant_type")
    private String grantType = "password";

    @SerializedName("client_id")
    private String clientId = "1";

    @SerializedName("client_secret")
    private String clientSecret = "67DPdYb9o9PjcS3sLH0NzXxRPVtPOcignJjdCKnR";

    @SerializedName("scope")
    private String scope = "";


    public LoginModel(final String userName, final String password) {
        this.userName = userName;
        this.password = password;
    }


}
