package com.freepadhe.GsonModel.JobDetailModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobDetailDetum
{
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("importantPoints")
    @Expose
    private String importantPoints;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("rating")
    @Expose
    private Float rating;

    @SerializedName("medium")
    @Expose
    private String medium;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }


    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
