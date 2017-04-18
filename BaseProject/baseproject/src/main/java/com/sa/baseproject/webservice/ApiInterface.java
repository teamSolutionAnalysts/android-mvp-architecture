package com.sa.baseproject.webservice;

import com.sa.baseproject.model.ContactModel;
import com.sa.baseproject.model.Github;
import com.sa.baseproject.model.LoginModel;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by sa on 14/06/16.
 *
 * api interface for generating request of webservice call.
 *
 */

public interface ApiInterface {

    @GET("users/{login}")
    Observable<Github> getProfile(@Path("login") String loginF);

    @GET("contacts/")
    Observable<ContactModel> getContact();

    @POST("oauth/token")
    Observable<LoginModel> login(@Body com.sa.baseproject.model.request.LoginModel loginModel);

}
