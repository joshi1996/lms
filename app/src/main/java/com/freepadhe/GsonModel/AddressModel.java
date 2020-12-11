package com.freepadhe.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
