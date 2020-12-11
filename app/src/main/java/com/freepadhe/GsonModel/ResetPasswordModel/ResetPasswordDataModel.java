package com.freepadhe.GsonModel.ResetPasswordModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordDataModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ResetPasswordData data;

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

    public ResetPasswordData getData() {
        return data;
    }

    public void setData(ResetPasswordData data) {
        this.data = data;
    }
}
