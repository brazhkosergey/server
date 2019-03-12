package com.education.simple.entity.school;

import com.education.simple.entity.Entity;

public class Lesson implements Entity {

    private int id;
    private int electiveCourseId;

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
