package com.sa.baseproject.appview.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sa.baseproject.R;
import com.sa.baseproject.appview.login.presenter.LoginPresenterImp;
import com.sa.baseproject.appview.login.view.LoginView;
import com.sa.baseproject.base.AppActivity;
import com.sa.baseproject.base.AppFragment;
import com.sa.baseproject.imagepicker.ImagePicker;
import com.sa.baseproject.imagepicker.ImagePickerInterface;
import com.sa.baseproject.model.LoginModel;
import com.sa.baseproject.utils.Constants;
import com.sa.baseproject.utils.DialogUtils;
import com.sa.baseproject.utils.LocationUtils;
import com.sa.baseproject.utils.PermissionUtils;
import com.sa.baseproject.webservice.ApiCallback;
import com.sa.baseproject.webservice.ApiErrorModel;
import com.sa.baseproject.webservice.ApiManager;


/**
 * Created by mavya.soni on 30/03/17.
 */

public class OneFragment extends AppFragment implements View.OnClickListener, LoginView, ImagePickerInterface,
        ImagePicker.OnGetBitmapListener, PermissionUtils.PermissionGranted {


    private EditText edtEmail;
    private EditText edtPassword;

    private ImageView imageView;

    private LoginPresenterImp loginPresenterImp;

    private ImagePicker imagePicker;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void initializeComponent(View view) {
        imageView = (ImageView) view.findViewById(R.id.fragment_1_iv_profile);
        edtEmail = (EditText) view.findViewById(R.id.fragment_1_edt_email);
        edtPassword = (EditText) view.findViewById(R.id.fragment_1_edt_password);
        final Button btnLogin = (Button) view.findViewById(R.id.fragment_1_btn_login);
        btnLogin.setOnClickListener(this);
        imageView.setOnClickListener(this);
        loginPresenterImp = new LoginPresenterImp(this);


    }

    @Override
    public void trackScreen() {

    }

    @Override
    protected void pageVisible() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fragment_1_btn_login:
//                final String email = edtEmail.getText().toString().trim();
//                final String password = edtPassword.getText().toString().trim();
//                loginPresenterImp.validateCredentials(email, password);
//                ((AppActivity) getActivity()).getAppFragmentManager().addFragment(AppFragmentState.F_DETAILS, null, true);


                break;
            case R.id.fragment_1_iv_profile:
                openImagePicker();
//                ((AppActivity) getActivity()).getAppFragmentManager().addFragment(AppFragmentState.F_TWO, null, true);
                break;
        }
    }

    @Override
    public void displayValidationMessage(int message) {
        DialogUtils.showSnackBar(getActivity(), getString(message));
    }

    @Override
    public void wsLogin() {
        final String email = edtEmail.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();

    }

    @Override
    public void handleCamera(Intent takePictureIntent) {
        startActivityForResult(takePictureIntent, ImagePicker.CAMERA_REQUEST);
    }

    @Override
    public void handleGallery(Intent galleryPickerIntent) {
        startActivityForResult(galleryPickerIntent, ImagePicker.GALLERY_REQUEST);
    }

    public void openImagePicker() {
        imagePicker = new ImagePicker(getActivity(), this);
        imagePicker.setOnGetBitmapListener(this);
        ((AppActivity) getActivity()).getPermissionUtils().setPermissionGranted(this);
        if (!((AppActivity) getActivity()).getPermissionUtils().checkPermission(PermissionUtils.PERMISSION_STORAGE, Constants.REQUEST_CODE_ASK_PERMISSIONS)) {
            imagePicker.createImageChooser();
        }
    }
//    public void openImagePicker() {
//
//        imagePicker = new ImagePicker(getActivity(), this);
//        imagePicker.setOnGetBitmapListener(this);
//        final int readPermissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
//        final int writePermissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if (readPermissionCheck != PackageManager.PERMISSION_GRANTED && writePermissionCheck != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        Const.REQUEST_CODE_ASK_PERMISSIONS);
//            }
//        } else {
//            imagePicker.createImageChooser();
//        }
//    }

    @Override
    public void onGetBitmap(Bitmap bitmap) {
        if (bitmap != null) {
//            imagePicker.getImageFile()
            imageView.setImageBitmap(bitmap);
        } else {
            DialogUtils.showSnackBar(getActivity(), "Image not found");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (imagePicker != null) {
            imagePicker.onActivityResult(requestCode, data != null ? data.getData() : null, data);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onPermissionGranted(int requestCode) {
        imagePicker.createImageChooser();
    }
}
