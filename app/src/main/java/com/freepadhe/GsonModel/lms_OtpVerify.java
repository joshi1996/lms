
package com.freepadhe.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class lms_OtpVerify {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private lmsData data;

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

    public lmsData getData() {
        return data;
    }

    public void setData(lmsData data) {
        this.data = data;
    }

}
