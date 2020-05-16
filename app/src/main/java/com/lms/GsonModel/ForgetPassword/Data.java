
package com.lms.GsonModel.ForgetPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("authtoken")
    @Expose
    private String authtoken;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("otp")
    @Expose
    private String otp;

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
