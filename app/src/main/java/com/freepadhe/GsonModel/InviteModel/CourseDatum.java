
package com.freepadhe.GsonModel.InviteModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseDatum {

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
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("gateway")
    @Expose
    private String gateway;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
