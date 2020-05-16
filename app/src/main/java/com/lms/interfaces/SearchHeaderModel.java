package com.lms.interfaces;

import com.lms.GsonModel.CourseContentList;
import com.lms.GsonModel.TopicModel.TopicDatum;

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
