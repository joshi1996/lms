package com.freepadhe.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOTP {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("UserName")
    @Expose
    private String userName;


    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;


    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getAuthApp() {
        return 11212124343535453453d;
    }

    public String getImage() {
        return "";
    }

    public String getName() {
        return "";
    }

    public Object getIsSocialLogin() {
        return "";
    }
}