package com.lms.interfaces;

import com.lms.GsonModel.CourseContentList;

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
