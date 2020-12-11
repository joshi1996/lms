package com.freepadhe.GsonModel.LiveVideo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveVideoDatum implements Parcelable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("courseId")
    @Expose
    private String courseId;
    @SerializedName("dateAndTime")
    @Expose
    private String dateAndTime;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("courseName")
    @Expose
    private String courseName;

    protected LiveVideoDatum(Parcel in) {
        id = in.readString();
        courseId = in.readString();
        dateAndTime = in.readString();
        title = in.readString();
        code = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        courseName = in.readString();
    }

    public static final Creator<LiveVideoDatum> CREATOR = new Creator<LiveVideoDatum>() {
        @Override
        public LiveVideoDatum createFromParcel(Parcel in) {
            return new LiveVideoDatum(in);
        }

        @Override
        public LiveVideoDatum[] newArray(int size) {
            return new LiveVideoDatum[size];
        }
    };

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

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(courseId);
        parcel.writeString(dateAndTime);
        parcel.writeString(title);
        parcel.writeString(code);
        parcel.writeString(description);
        if (status == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(status);
        }
        parcel.writeString(courseName);
    }
}
