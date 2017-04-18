package com.sa.baseproject.appview.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sa.baseproject.R;
import com.sa.baseproject.appview.signup.presenter.SignUpPresenterImp;
import com.sa.baseproject.appview.signup.view.SignUpView;
import com.sa.baseproject.base.AppFragment;
import com.sa.baseproject.utils.AlertDialogUtils;
import com.sa.baseproject.utils.ProgressUtils;

/**
 * Created by sa on 17/04/17.
 *
 */

public class SignUpFragment extends AppFragment implements SignUpView, View.OnClickListener {

    private EditText edtEmail;
    private EditText edtPassword;
    private SignUpPresenterImp signUpPresenterImp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    protected void initializeComponent(View view) {
        edtEmail = (EditText) view.findViewById(R.id.fragment_signup_edt_email);
        edtPassword = (EditText) view.findViewById(R.id.fragment_signup_edt_password);
        final Button btnSignUp = (Button) view.findViewById(R.id.fragment_signup_btn_signup);
        btnSignUp.setOnClickListener(this);
        signUpPresenterImp = new SignUpPresenterImp(this);
    }

    @Override
    protected void trackScreen() {

    }

    @Override
    protected void pageVisible() {

    }


    @Override
    public void displayValidationMessage(int message) {
        AlertDialogUtils.showErrorDialog(getActivity(), getString(message));
    }


    @Override
    public void showProgressDialog() {
        ProgressUtils.getInstance(getActivity()).show();
    }

    @Override
    public void dismissProgressDialog() {
        ProgressUtils.getInstance(getActivity()).close();
    }

    @Override
    public void displayValidationMessage(String message) {
        AlertDialogUtils.showErrorDialog(getActivity(), message);
    }

    @Override
    public void onSignSuccess() {

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fragment_signup_btn_signup:
                final String email = edtEmail.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();
                signUpPresenterImp.validateCredentials(email, password);
                break;
        }
    }
}
