package com.sa.baseproject.webservice.manager;

import android.app.ProgressDialog;
import android.content.Context;


import com.sa.baseproject.App;
import com.sa.baseproject.model.ContactModel;
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
 * All contact web service methods should be created in this class
 */

public class ContactModule {

    private static ContactModule instance;

    public static ContactModule getInstance() {
        if (instance == null) {
            instance = new ContactModule();
        }
        return instance;
    }


    public void getContact(final Context context, ApiCallback<ContactModel> apiCallback) {
        ProgressUtils.getInstance(context).show();
        final Observable<ContactModel> observable = App
                .getInstance()
                .getApiService()
                .getApiInterface()
                .getContact()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        ApiHandle.createRetrofitBase(context, observable, apiCallback);
    }
}
