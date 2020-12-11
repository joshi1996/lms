package com.freepadhe.GsonModel.SubmitReviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitReviewDatum {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("courseId")
    @Expose
    private String courseId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

}
