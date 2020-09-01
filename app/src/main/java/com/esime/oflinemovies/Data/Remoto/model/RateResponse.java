package com.esime.oflinemovies.Data.Remoto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateResponse {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("status_code")
    @Expose
    private int statusCode;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}