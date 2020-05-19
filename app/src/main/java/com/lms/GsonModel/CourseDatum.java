
package com.lms.GsonModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseDatum implements Parcelable {

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
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("rating")
    @Expose
    private Float rating;



    @SerializedName("medium")
    @Expose
    private String medium;


    protected CourseDatum(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        importantPoints = in.readString();
        image = in.readString();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readFloat();
        }
        medium = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(importantPoints);
        dest.writeString(image);
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(rating);
        }
        dest.writeString(medium);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CourseDatum> CREATOR = new Creator<CourseDatum>() {
        @Override
        public CourseDatum createFromParcel(Parcel in) {
            return new CourseDatum(in);
        }

        @Override
        public CourseDatum[] newArray(int size) {
            return new CourseDatum[size];
        }
    };

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }


    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
