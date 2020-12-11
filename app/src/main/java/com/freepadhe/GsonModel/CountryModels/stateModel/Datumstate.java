
package com.freepadhe.GsonModel.CountryModels.stateModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datumstate {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("countryId")
    @Expose
    private String countryId;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
