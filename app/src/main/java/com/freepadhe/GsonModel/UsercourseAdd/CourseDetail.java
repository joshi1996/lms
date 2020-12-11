
package com.freepadhe.GsonModel.UsercourseAdd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseDetail {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("importantPoints")
    @Expose
    private String importantPoints;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImportantPoints() {
        return importantPoints;
    }

    public void setImportantPoints(String importantPoints) {
        this.importantPoints = importantPoints;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
