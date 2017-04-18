package com.sa.baseproject.webservice;

import android.content.Context;

import com.sa.baseproject.model.ContactModel;
import com.sa.baseproject.model.LoginModel;
import com.sa.baseproject.webservice.manager.AuthorisationModule;
import com.sa.baseproject.webservice.manager.ContactModule;


/**
 * Created by mavya.soni on 31/03/17.
 */

public class ApiManager {

    private static AuthorisationModule authorisationModule;
    private static ContactModule contactModule;

    private static AuthorisationModule getAuthorisationModule() {
        if (authorisationModule == null) {
            authorisationModule = AuthorisationModule.getInstance();
        }
        return authorisationModule;
    }


    private static ContactModule getContactModule() {
        if (contactModule == null) {
            contactModule = ContactModule.getInstance();
        }
        return contactModule;
    }


    public static void getProfile(final Context context, String user, ApiCallback apiCallback) {
//        getAuthorisationModule().getProfile(context, user, apiCallback);
    }

    public static void getContact(final Context context, ApiCallback<ContactModel> apiCallback) {
        getContactModule().getContact(context, apiCallback);
    }

    public static void login(final com.sa.baseproject.model.request.LoginModel loginModel, ApiCallback<LoginModel> apiCallback) {
        getAuthorisationModule().login(loginModel, apiCallback);
    }

}
