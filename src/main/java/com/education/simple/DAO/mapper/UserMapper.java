package com.education.simple.DAO.mapper;

import com.education.simple.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSecondName(resultSet.getString("second_name"));
        user.setMailAddress(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRegistrationDate(resultSet.getLong("registration_date"));
        return user;
    }
}
