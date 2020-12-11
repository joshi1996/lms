package com.freepadhe.GsonModel.EarningModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferralModel
{
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("referredByUserId")
    @Expose
    private String referredByUserId;
    @SerializedName("referredToUserId")
    @Expose
    private String referredToUserId;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("courseId")
    @Expose
    private String courseId;
    @SerializedName("userCourseId")
    @Expose
    private String userCourseId;
    @SerializedName("isRedeemed")
    @Expose
    private Integer isRedeemed;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("earningId")
    @Expose
    private String earningId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReferredByUserId() {
        return referredByUserId;
    }

    public void setReferredByUserId(String referredByUserId) {
        this.referredByUserId = referredByUserId;
    }

    public String getReferredToUserId() {
        return referredToUserId;
    }

    public void setReferredToUserId(String referredToUserId) {
        this.referredToUserId = referredToUserId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getUserCourseId() {
        return userCourseId;
    }

    public void setUserCourseId(String userCourseId) {
        this.userCourseId = userCourseId;
    }

    public Integer getIsRedeemed() {
        return isRedeemed;
    }

    public void setIsRedeemed(Integer isRedeemed) {
        this.isRedeemed = isRedeemed;
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

    public String getEarningId() {
        return earningId;
    }

    public void setEarningId(String earningId) {
        this.earningId = earningId;
    }
}
