package com.freepadhe.GsonModel.EarningModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DatumEarning
{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("isPaid")
    @Expose
    private Integer isPaid;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("paidBy")
    @Expose
    private String paidBy;
    @SerializedName("paidDate")
    @Expose
    private String paidDate;
    @SerializedName("paymentDetails")
    @Expose
    private String paymentDetails;
    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("referrals")
    @Expose
    private List<ReferralModel> referrals = null;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("paidByfullName")
    @Expose
    private String paidByfullName;
    @SerializedName("emailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("whatsappNo")
    @Expose
    private String whatsappNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public List<ReferralModel> getReferrals() {
        return referrals;
    }

    public void setReferrals(List<ReferralModel> referrals) {
        this.referrals = referrals;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPaidByfullName() {
        return paidByfullName;
    }

    public void setPaidByfullName(String paidByfullName) {
        this.paidByfullName = paidByfullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getWhatsappNo() {
        return whatsappNo;
    }

    public void setWhatsappNo(String whatsappNo) {
        this.whatsappNo = whatsappNo;
    }

}
