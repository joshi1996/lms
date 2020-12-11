package com.freepadhe.interfaces;

import com.freepadhe.GsonModel.CourseContentList;

public interface ListItem {
    boolean isHeader();
    String getName();
    CourseContentList getData();

}
