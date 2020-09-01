package com.esime.oflinemovies.Data.Remoto.Request;

import com.bumptech.glide.request.RequestCoordinator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestToken {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("request_token")
    @Expose
    private String requestToken;


    public RequestToken(String username, String password, String requestToken){
        this.username = username;
        this.password = password;
        this.requestToken = requestToken;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

}