
package com.freepadhe.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class lmsUser {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("emailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("profilePhoto")
    @Expose
    private String profilePhoto;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("city")
    @Expose
    private String city;


    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("cityName")
    @Expose
    private String cityName;

    @SerializedName("stateName")
    @Expose
    private String stateName;

    @SerializedName("countryName")
    @Expose
    private String countryName;

    @SerializedName("roleId")
    @Expose
    private String roleId;

    /*@SerializedName("roleName")
    @Expose
    private String roleName;*/

    @SerializedName("userCode")
    @Expose
    private String userCode;


    @SerializedName("zipCode")
    @Expose
    private String zipCode;

    @SerializedName("address")
    @Expose
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /*public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }*/

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
