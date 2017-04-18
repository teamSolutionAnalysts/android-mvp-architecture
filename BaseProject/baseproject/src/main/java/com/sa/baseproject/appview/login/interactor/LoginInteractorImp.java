package com.sa.baseproject.appview.login.interactor;

import android.text.TextUtils;

import com.sa.baseproject.R;


/**
 * Created by sa on 03/04/17.
 *
 * InteractorImp Class for business login of Login Screen
 *
 */

public class LoginInteractorImp implements LoginInteractor {
    @Override
    public void login(String username, String password, OnLoginFinishedListener listener) {
        if (TextUtils.isEmpty(username)) {
            listener.onError(R.string.msg_valid_email);
        } else if (TextUtils.isEmpty(password)) {
            listener.onError(R.string.msg_valid_password);
        } else {
            listener.onLoginSuccess();
        }
    }
}
