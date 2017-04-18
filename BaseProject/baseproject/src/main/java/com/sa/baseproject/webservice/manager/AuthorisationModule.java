package com.sa.baseproject.webservice.manager;

import android.app.ProgressDialog;
import android.content.Context;

import com.sa.baseproject.App;
import com.sa.baseproject.model.LoginModel;
import com.sa.baseproject.utils.DialogUtils;
import com.sa.baseproject.utils.ProgressUtils;
import com.sa.baseproject.webservice.ApiCallback;
import com.sa.baseproject.webservice.ApiHandle;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sa on 01/04/17.
 * <p>
 * All authorisation web service methods should be created in this class
 */

public class AuthorisationModule {

    private static AuthorisationModule authorisationModule;

    public static AuthorisationModule getInstance() {
        if (authorisationModule == null) {
            authorisationModule = new AuthorisationModule();
        }
        return authorisationModule;
    }

    public void login(final com.sa.baseproject.model.request.LoginModel loginModel,
                      ApiCallback<LoginModel> apiCallback) {
        final Observable<LoginModel> observable = App
                .getInstance()
                .getApiService()
                .getApiInterface()
                .login(loginModel)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        ApiHandle.createRetrofitBase(observable, apiCallback);

    }


}
