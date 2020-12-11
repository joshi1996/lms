package com.freepadhe.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LmsLogindata {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private logindata data;

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

    public logindata getData() {
        return data;
    }

    public void setData(logindata data) {
        this.data = data;
    }
}
