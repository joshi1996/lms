package com.freepadhe.GsonModel.SubmitReviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubmitReviewModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<SubmitReviewDatum> data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SubmitReviewDatum> getData() {
        return data;
    }

    public void setData(List<SubmitReviewDatum> data) {
        this.data = data;
    }

}
