
package com.freepadhe.GsonModel.UserCourse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable {

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

    @SerializedName("transactionId")
    @Expose
    private String transactionId;

    @SerializedName("amount")
    @Expose
    private Integer amount;


    @SerializedName("gateway")
    @Expose
    private String gateway;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;


    protected Datum(Parcel in) {
        id = in.readString();
        userId = in.readString();
        courseId = in.readString();
        contentPlanType = in.readString();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        courseDetail = in.readParcelable(CourseDetail.class.getClassLoader());
        transactionId = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readInt();
        }
        gateway = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(courseId);
        dest.writeString(contentPlanType);
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        dest.writeParcelable(courseDetail, flags);
        dest.writeString(transactionId);
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(amount);
        }
        dest.writeString(gateway);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Datum> CREATOR = new Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };

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
