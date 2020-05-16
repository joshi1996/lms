
package com.lms.GsonModel.UsercourseAdd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("courseId")
    @Expose
    private String courseId;
    @SerializedName("contentPlanType")
    @Expose
    private String contentPlanType;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("courseDetail")
    @Expose
    private CourseDetail courseDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getContentPlanType() {
        return contentPlanType;
    }

    public void setContentPlanType(String contentPlanType) {
        this.contentPlanType = contentPlanType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CourseDetail getCourseDetail() {
        return courseDetail;
    }

    public void setCourseDetail(CourseDetail courseDetail) {
        this.courseDetail = courseDetail;
    }

}
