package com.lms.interfaces;

import com.lms.GsonModel.CourseContentList;

public interface ListItem {
    boolean isHeader();
    String getName();
    CourseContentList getData();

}
