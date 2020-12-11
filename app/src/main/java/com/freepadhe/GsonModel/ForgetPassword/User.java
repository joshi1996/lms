
package com.freepadhe.GsonModel.ForgetPassword;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("emailAddress")
    @Expose
    private String emailAddress;
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
    @SerializedName("profilePhoto")
    @Expose
    private String profilePhoto;
    @SerializedName("roleId")
    @Expose
    private String roleId;
   /* @SerializedName("roleName")
    @Expose
    private List<String> roleName = null;*/

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("userCode")
    @Expose
    private String userCode;


    protected User(Parcel in) {
        id = in.readString();
        fullName = in.readString();
        phoneNo = in.readString();
        emailAddress = in.readString();
        country = in.readString();
        state = in.readString();
        city = in.readString();
        cityName = in.readString();
        stateName = in.readString();
        countryName = in.readString();
        profilePhoto = in.readString();
        roleId = in.readString();
        //roleName = in.createStringArrayList();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        userCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(fullName);
        dest.writeString(phoneNo);
        dest.writeString(emailAddress);
        dest.writeString(country);
        dest.writeString(state);
        dest.writeString(city);
        dest.writeString(cityName);
        dest.writeString(stateName);
        dest.writeString(countryName);
        dest.writeString(profilePhoto);
        dest.writeString(roleId);
        //dest.writeStringList(roleName);
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        dest.writeString(userCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    /*public List<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(List<String> roleName) {
        this.roleName = roleName;
    }*/
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
