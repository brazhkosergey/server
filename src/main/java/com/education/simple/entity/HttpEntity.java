package com.education.simple.entity;

import com.education.simple.enums.EntityType;

public interface HttpEntity {
    EntityType getEntityType();

    String getName();

    String getImagePath();

    String getFirstButtonName();

    String getSecondButtonName();

    String getThirdButtonName();

    String getFourthButtonName();

    String getFifthButtonName();
}
