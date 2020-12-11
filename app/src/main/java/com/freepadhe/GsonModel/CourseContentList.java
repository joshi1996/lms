
package com.freepadhe.GsonModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseContentList implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("course")
    @Expose
    private String course;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("contentType")
    @Expose
    private String contentType;
    @SerializedName("videoType")
    @Expose
    private String videoType;
    @SerializedName("contentPlanType")
    @Expose
    private String contentPlanType;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("pdf")
    @Expose
    private String pdf;
    @SerializedName("videoUrl")
    @Expose
    private String videoUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("courseName")
    @Expose
    private String courseName;
    @SerializedName("subjectName")
    @Expose
    private String subjectName;
    @SerializedName("topicName")
    @Expose
    private String topicName;
    @SerializedName("status")
    @Expose
    private Integer status;

    boolean showchild=true;

    protected CourseContentList(Parcel in) {
        id = in.readString();
        course = in.readString();
        subject = in.readString();
        topic = in.readString();
        contentType = in.readString();
        videoType = in.readString();
        contentPlanType = in.readString();
        title = in.readString();
        video = in.readString();
        photo = in.readString();
        pdf = in.readString();
        description = in.readString();
        courseName = in.readString();
        subjectName = in.readString();
        topicName = in.readString();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(course);
        dest.writeString(subject);
        dest.writeString(topic);
        dest.writeString(contentType);
        dest.writeString(videoType);
        dest.writeString(contentPlanType);
        dest.writeString(title);
        dest.writeString(video);
        dest.writeString(photo);
        dest.writeString(pdf);
        dest.writeString(description);
        dest.writeString(courseName);
        dest.writeString(subjectName);
        dest.writeString(topicName);
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CourseContentList> CREATOR = new Creator<CourseContentList>() {
        @Override
        public CourseContentList createFromParcel(Parcel in) {
            return new CourseContentList(in);
        }

        @Override
        public CourseContentList[] newArray(int size) {
            return new CourseContentList[size];
        }
    };

    public boolean isShowchild() {
        return showchild;
    }

    public void setShowchild(boolean showchild) {
        this.showchild = showchild;
    }

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getContentPlanType() {
        return contentPlanType;
    }

    public void setContentPlanType(String contentPlanType) {
        this.contentPlanType = contentPlanType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
