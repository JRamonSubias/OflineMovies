package com.esime.oflinemovies.Data.Remoto.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestRate {

    @SerializedName("value")
    @Expose
    private double value;

    public RequestRate(double rating){
        value = rating;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}