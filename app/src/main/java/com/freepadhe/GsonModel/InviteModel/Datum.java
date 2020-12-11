
package com.freepadhe.GsonModel.InviteModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("emailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("zipCode")
    @Expose
    private String zipCode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("cityName")
    @Expose
    private String cityName;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("countryName")
    @Expose
    private String countryName;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("profilePhoto")
    @Expose
    private String profilePhoto;
    @SerializedName("roleId")
    @Expose
    private String roleId;
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("userCode")
    @Expose
    private String userCode;
    @SerializedName("friendCode")
    @Expose
    private String friendCode;
    @SerializedName("accountHolderName")
    @Expose
    private String accountHolderName;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("IFSCode")
    @Expose
    private String iFSCode;
    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("googleMobileNo")
    @Expose
    private String googleMobileNo;
    @SerializedName("paytmMobileNo")
    @Expose
    private String paytmMobileNo;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("courseData")
    @Expose
    private List<CourseDatum> courseData = null;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getFriendCode() {
        return friendCode;
    }

    public void setFriendCode(String friendCode) {
        this.friendCode = friendCode;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIFSCode() {
        return iFSCode;
    }

    public void setIFSCode(String iFSCode) {
        this.iFSCode = iFSCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getGoogleMobileNo() {
        return googleMobileNo;
    }

    public void setGoogleMobileNo(String googleMobileNo) {
        this.googleMobileNo = googleMobileNo;
    }

    public String getPaytmMobileNo() {
        return paytmMobileNo;
    }

    public void setPaytmMobileNo(String paytmMobileNo) {
        this.paytmMobileNo = paytmMobileNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CourseDatum> getCourseData() {
        return courseData;
    }

    public void setCourseData(List<CourseDatum> courseData) {
        this.courseData = courseData;
    }

}
