
package com.lms.GsonModel.citydata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datumcity {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("stateId")
    @Expose
    private String stateId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
