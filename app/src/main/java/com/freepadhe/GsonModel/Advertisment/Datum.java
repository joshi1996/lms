
package com.freepadhe.GsonModel.Advertisment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("URL")
    @Expose
    private String uRL;
    @SerializedName("screen")
    @Expose
    private String screen;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
