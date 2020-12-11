package com.freepadhe.interfaces;


import com.freepadhe.GsonModel.CourseContentList;
import com.freepadhe.GsonModel.TopicModel.TopicDatum;

public interface ListItem1 {
    boolean isHeader();
    TopicDatum getHeaderData();
    CourseContentList getChildData();

}
