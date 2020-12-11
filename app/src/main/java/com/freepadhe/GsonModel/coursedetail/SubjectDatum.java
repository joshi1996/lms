
package com.freepadhe.GsonModel.coursedetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectDatum implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("course")
    @Expose
    private String course;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("lessoncount")
    @Expose
    private Integer lessoncount;


    protected SubjectDatum(Parcel in) {
        id = in.readString();
        course = in.readString();
        name = in.readString();
        description = in.readString();
        image = in.readString();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        if (in.readByte() == 0) {
            lessoncount = null;
        } else {
            lessoncount = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(course);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(image);
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        if (lessoncount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(lessoncount);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubjectDatum> CREATOR = new Creator<SubjectDatum>() {
        @Override
        public SubjectDatum createFromParcel(Parcel in) {
            return new SubjectDatum(in);
        }

        @Override
        public SubjectDatum[] newArray(int size) {
            return new SubjectDatum[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getLessoncount() {
        return lessoncount;
    }

    public void setLessoncount(Integer lessoncount) {
        this.lessoncount = lessoncount;
    }
}
