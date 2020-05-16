package com.lms.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lms.GsonModel.ForgetPassword.User;

public class logindata {
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("user")
    @Expose
    private User user;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
