package com.education.simple.entity.school;

import com.education.simple.entity.Entity;

public class School implements Entity {
    private int id;
    private String ownerId;
    private String nameOfSchool;

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
