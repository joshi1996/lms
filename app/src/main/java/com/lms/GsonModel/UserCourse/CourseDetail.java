
package com.lms.GsonModel.UserCourse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseDetail implements Parcelable {

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

    @SerializedName("rating")
    @Expose
    private Float rating;


    protected CourseDetail(Parcel in) {
        id = in.readString();
        medium = in.readString();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        name = in.readString();
        description = in.readString();
        importantPoints = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        image = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readFloat();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(medium);
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(importantPoints);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(image);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(rating);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CourseDetail> CREATOR = new Creator<CourseDetail>() {
        @Override
        public CourseDetail createFromParcel(Parcel in) {
            return new CourseDetail(in);
        }

        @Override
        public CourseDetail[] newArray(int size) {
            return new CourseDetail[size];
        }
    };

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
