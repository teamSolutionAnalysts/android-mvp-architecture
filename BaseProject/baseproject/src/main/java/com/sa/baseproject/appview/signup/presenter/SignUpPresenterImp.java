package com.sa.baseproject.appview.signup.presenter;

import android.text.TextUtils;

import com.sa.baseproject.R;
import com.sa.baseproject.appview.signup.view.SignUpView;
import com.sa.baseproject.model.LoginModel;
import com.sa.baseproject.webservice.ApiCallback;
import com.sa.baseproject.webservice.ApiErrorModel;
import com.sa.baseproject.webservice.ApiManager;

/**
 * Created by sa on 03/04/17.
 * PresenterImp  Class for bridge between Business logic and view
 */

public class SignUpPresenterImp implements SignUpPresenter {

    private SignUpView signUpView;

    public SignUpPresenterImp(SignUpView loginView) {
        this.signUpView = loginView;
    }

    @Override
    public void validateCredentials(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            signUpView.displayValidationMessage(R.string.msg_valid_email);
        } else if (TextUtils.isEmpty(password)) {
            signUpView.displayValidationMessage(R.string.msg_valid_password);
        } else {
            signUpView.showProgressDialog();
            final com.sa.baseproject.model.request.LoginModel loginModel = new com.sa.baseproject.model.request.LoginModel(username, password);
            ApiManager.login(loginModel, new ApiCallback<LoginModel>() {
                @Override
                public void onSuccess(LoginModel loginModel) {
                    signUpView.displayValidationMessage("Success");
                    signUpView.dismissProgressDialog();
                    signUpView.onSignSuccess();
                }

                @Override
                public void onFailure(ApiErrorModel apiErrorModel) {
                    if (apiErrorModel != null) {
                        signUpView.displayValidationMessage(apiErrorModel.getMessage());
                    }
                    signUpView.dismissProgressDialog();
                }
            });

        }
    }
}
