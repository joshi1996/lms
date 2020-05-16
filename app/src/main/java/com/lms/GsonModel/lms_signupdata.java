package com.lms.GsonModel;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lms.GsonModel.ForgetPassword.User;

public class lms_signupdata  {

    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("userData")
    @Expose
    private User userData;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }
}
