package com.education.simple.entity;

import com.education.simple.enums.EntityType;

public interface HttpEntity {
    int getId();

    EntityType getEntityType();

    String getName();
    String getSecondName();
    String getEmail();

    int getLikesCount();

    int getViewsCount();

    int getCommentsCount();

    String getImagePath();

    String getFirstButtonName();

    String getSecondButtonName();

    String getThirdButtonName();

    String getFourthButtonName();

    String getFifthButtonName();
}
