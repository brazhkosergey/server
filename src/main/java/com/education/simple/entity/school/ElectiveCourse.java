package com.education.simple.entity.school;

import com.education.simple.entity.Entity;

public class ElectiveCourse implements Entity {

    private int id;
    private int schoolId;
    private int teacherId;
    private String nameOfCourse;

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
