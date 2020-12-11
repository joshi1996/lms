package com.freepadhe.interfaces;

import com.freepadhe.GsonModel.CourseContentList;

public class ChildModel implements ListItem {

    CourseContentList child;

    public void setChild(CourseContentList child) {
        this.child = child;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public String getName() {
        return child.getTitle();
    }

    @Override
    public CourseContentList getData() {
        return child;
    }
}
