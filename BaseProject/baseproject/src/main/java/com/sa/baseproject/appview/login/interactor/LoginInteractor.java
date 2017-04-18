package com.sa.baseproject.appview.login.interactor;

/**
 * Created by sa on 03/04/17.
 *
 *
 */

public interface LoginInteractor {

    interface OnLoginFinishedListener {
        void onError(final int message);

        void onLoginSuccess();
    }

    void login(String username, String password, OnLoginFinishedListener listener);


}
