package com.freepadhe.GsonModel.JobDetailModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobDetailModel
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
    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("subjectData")
    @Expose
    private List<JobDetailSubjectDatum> subjectData = null;
    @SerializedName("planData")
    @Expose
    private List<JobDetailPlanDatum> planData = null;
    @SerializedName("usersCourseAdded")
    @Expose
    private Integer usersCourseAdded;

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

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<JobDetailSubjectDatum> getSubjectData() {
        return subjectData;
    }

    public void setSubjectData(List<JobDetailSubjectDatum> subjectData) {
        this.subjectData = subjectData;
    }

    public List<JobDetailPlanDatum> getPlanData() {
        return planData;
    }

    public void setPlanData(List<JobDetailPlanDatum> planData) {
        this.planData = planData;
    }

    public Integer getUsersCourseAdded() {
        return usersCourseAdded;
    }

    public void setUsersCourseAdded(Integer usersCourseAdded) {
        this.usersCourseAdded = usersCourseAdded;
    }

}
