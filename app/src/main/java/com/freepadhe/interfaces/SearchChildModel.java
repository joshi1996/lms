package com.freepadhe.interfaces;

import com.freepadhe.GsonModel.CourseContentList;
import com.freepadhe.GsonModel.TopicModel.TopicDatum;

public class SearchChildModel implements ListItem1 {

    CourseContentList child;

    public void setChild(CourseContentList child) {
        this.child = child;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public TopicDatum getHeaderData() {
        return null;
    }


    @Override
    public CourseContentList getChildData() {
        return child;
    }
}
