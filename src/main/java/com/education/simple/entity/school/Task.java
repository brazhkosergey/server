package com.education.simple.entity.school;

import com.education.simple.entity.Entity;

public class Task implements Entity {
    private int id;

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
