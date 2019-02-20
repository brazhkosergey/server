package com.education.simple.DAO;

import com.education.simple.entity.Entity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Objects;

public class StaticService {

    public static void setIdToEntity(JdbcTemplate jdbcTemplate,PreparedStatementCreator psc, Entity entity){
        final KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, holder);
        final int newNameId = Objects.requireNonNull(holder.getKey()).intValue();
        entity.setId(newNameId);
    }
}
