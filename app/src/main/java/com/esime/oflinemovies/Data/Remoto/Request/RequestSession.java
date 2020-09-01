package com.esime.oflinemovies.Data.Remoto.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSession {

    @SerializedName("request_token")
    @Expose
    private String requestToken;

    public RequestSession(String requestToken){
        this.requestToken = requestToken;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }
}
