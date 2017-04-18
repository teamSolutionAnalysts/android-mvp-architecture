package com.sa.baseproject.webservice;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.sa.baseproject.utils.DialogUtils;
import com.sa.baseproject.utils.ProgressUtils;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by sa on 28/06/16.
 * <p>
 * This class for generating api call.
 */
public class ApiHandle {

    public static <T> void createRetrofitBase(Observable<T> observable,
                                              final ApiCallback<T> apiCallback) {

        observable.subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable error) {
                ApiErrorModel responseModel = null;
                if (error instanceof HttpException) {
                    try {
                        final HttpException throwable = (HttpException) error;
                        final Response response = throwable.response();
                        final Gson gson = new Gson();
                        final String responseString = response.errorBody().string();
                        responseModel = gson.fromJson(responseString, ApiErrorModel.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                apiCallback.onFailure(responseModel);
            }

            @Override
            public void onNext(T t) {
                apiCallback.onSuccess(t);
            }
        });
    }

    public static <T> void createRetrofitBase(final Context context,
                                              Observable<T> observable,
                                              final ApiCallback<T> apiCallback) {

        observable.subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable error) {
                ApiErrorModel responseModel = null;
                if (error instanceof HttpException) {
                    try {
                        final HttpException throwable = (HttpException) error;
                        final Response response = throwable.response();
                        final Gson gson = new Gson();
                        final String responseString = response.errorBody().string();
                        responseModel = gson.fromJson(responseString, ApiErrorModel.class);
                        DialogUtils.dialog(context, responseModel.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                        DialogUtils.dialog(context, ApiConstant.SOMETHING_WRONG);
                    }
                } else {
                    DialogUtils.dialog(context, ApiConstant.SOMETHING_WRONG);
                }
                apiCallback.onFailure(responseModel);
                ProgressUtils.getInstance(context).close();
            }

            @Override
            public void onNext(T t) {
                apiCallback.onSuccess(t);
                ProgressUtils.getInstance(context).close();
            }
        });
    }

}
