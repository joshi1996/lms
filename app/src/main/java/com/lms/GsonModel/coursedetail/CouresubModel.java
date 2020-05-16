
package com.lms.GsonModel.coursedetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouresubModel {

    @SerializedName("message")
    @Expose
    private String message;
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
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("subjectData")
    @Expose
    private List<SubjectDatum> subjectData = null;
    @SerializedName("planData")
    @Expose
    private List<PlanDatum> planData = null;
    @SerializedName("lessoncount")
    @Expose
    private Integer lessoncount;

    @SerializedName("rating")
    @Expose
    private Float rating;


    @SerializedName("usersCourseAdded")
    @Expose
    private Integer usersCourseAdded;


    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SubjectDatum> getSubjectData() {
        return subjectData;
    }

    public void setSubjectData(List<SubjectDatum> subjectData) {
        this.subjectData = subjectData;
    }

    public List<PlanDatum> getPlanData() {
        return planData;
    }

    public void setPlanData(List<PlanDatum> planData) {
        this.planData = planData;
    }

    public Integer getLessoncount() {
        return lessoncount;
    }

    public void setLessoncount(Integer lessoncount) {
        this.lessoncount = lessoncount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUsersCourseAdded() {
        return usersCourseAdded;
    }

    public void setUsersCourseAdded(Integer usersCourseAdded) {
        this.usersCourseAdded = usersCourseAdded;
    }
}
