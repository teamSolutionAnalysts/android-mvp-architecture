package com.sa.baseproject.appview.login.presenter;

import com.sa.baseproject.appview.login.interactor.LoginInteractor;
import com.sa.baseproject.appview.login.interactor.LoginInteractorImp;
import com.sa.baseproject.appview.login.view.LoginView;

/**
 * Created by sa on 03/04/17.
 * PresenterImp  Class for bridge between Business logic and view
 */

public class LoginPresenterImp implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImp(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImp();
    }

    @Override
    public void validateCredentials(String username, String password) {
        loginInteractor.login(username, password, this);
    }

    @Override
    public void onError(int message) {
        loginView.displayValidationMessage(message);
    }

    @Override
    public void onLoginSuccess() {
        loginView.wsLogin();
    }
}
