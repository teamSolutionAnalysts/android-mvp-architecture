package com.sa.baseproject.webservice;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sa on 31/03/17.
 *
 * If we got failure in our webservice that time we have to display web service message that time this model would use.
 *
 */

public class ApiErrorModel {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private String error;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
