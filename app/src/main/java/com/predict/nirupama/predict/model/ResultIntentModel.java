package com.predict.nirupama.predict.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultIntentModel implements Serializable {
    public PredictAPIResponseModel getModel() {
        return model;
    }

    public void setModel(PredictAPIResponseModel model) {
        this.model = model;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    @SerializedName("model")
    @Expose
    private
    PredictAPIResponseModel model;

    @SerializedName("image")
    @Expose
    private
    Integer image;
}
