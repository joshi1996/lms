
package com.lms.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class lmsData  {

    @SerializedName("authtoken")
    @Expose
    private String authtoken;
    @SerializedName("user")
    @Expose
    private lmsUser user;

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public lmsUser getUser() {
        return user;
    }

    public void setUser(lmsUser user) {
        this.user = user;
    }

    public Double getAuthApp() {
        return null;
    }

    public Double getId() {
        return  null;
    }
}
