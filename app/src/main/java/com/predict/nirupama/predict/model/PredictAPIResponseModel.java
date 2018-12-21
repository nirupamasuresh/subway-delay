package com.predict.nirupama.predict.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PredictAPIResponseModel implements Serializable{
    @SerializedName("delays")
    @Expose
    private List<String> delays = null;

    public List<String> getDelays() {
        return delays;
    }

    public void setDelays(List<String> delays) {
        this.delays = delays;
    }
}
