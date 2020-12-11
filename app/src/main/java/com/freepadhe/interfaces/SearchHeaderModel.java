package com.freepadhe.interfaces;

import com.freepadhe.GsonModel.CourseContentList;
import com.freepadhe.GsonModel.TopicModel.TopicDatum;

public class SearchHeaderModel implements ListItem1 {

    TopicDatum header;

    public void setheader(TopicDatum header) {
        this.header = header;
    }

    @Override
    public boolean isHeader() {
        return true;
    }

    @Override
    public TopicDatum getHeaderData() {
        return header;
    }

    @Override
    public CourseContentList getChildData() {
        return null;
    }


}
