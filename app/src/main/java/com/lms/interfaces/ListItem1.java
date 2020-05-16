package com.lms.interfaces;


import com.lms.GsonModel.CourseContentList;
import com.lms.GsonModel.TopicModel.TopicDatum;

public interface ListItem1 {
    boolean isHeader();
    TopicDatum getHeaderData();
    CourseContentList getChildData();

}
