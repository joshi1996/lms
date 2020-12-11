
package com.freepadhe.GsonModel.TopicModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.freepadhe.GsonModel.CourseContentList;

public class TopicDatum {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("courseContentList")
    @Expose
    private List<CourseContentList> courseContentList = null;

    boolean showchild=true;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CourseContentList> getCourseContentList() {
        return courseContentList;
    }

    public void setCourseContentList(List<CourseContentList> courseContentList) {
        this.courseContentList = courseContentList;
    }

}
