package com.freepadhe.GsonModel.Job1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Job1Model
{
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Job1Data data;

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

    public Job1Data getData() {
        return data;
    }

    public void setData(Job1Data data) {
        this.data = data;
    }
}
