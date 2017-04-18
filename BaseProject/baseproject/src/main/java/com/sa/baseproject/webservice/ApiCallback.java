package com.sa.baseproject.webservice;

public interface ApiCallback<T> {
    public void onSuccess(T t);

    public void onFailure(ApiErrorModel apiErrorModel);
}
