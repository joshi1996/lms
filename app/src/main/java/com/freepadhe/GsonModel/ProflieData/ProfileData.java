
package com.freepadhe.GsonModel.ProflieData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.freepadhe.GsonModel.lmsUser;

public class ProfileData {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private lmsUser data;

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

    public lmsUser getData() {
        return data;
    }

    public void setData(lmsUser data) {
        this.data = data;
    }

}
