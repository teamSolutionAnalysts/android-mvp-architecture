package com.sa.baseproject.appview.signup.view;

/**
 * Created by sa on 17/04/17.
 *
 */

public interface SignUpView {
    void displayValidationMessage(final int message);

    void showProgressDialog();

    void dismissProgressDialog();

    void displayValidationMessage(final String message);

    void onSignSuccess();
}
