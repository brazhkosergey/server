package com.education.simple.DAO.mapper;

import com.education.simple.entity.Chat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Chat chat = new Chat();
        chat.setId(resultSet.getInt("id"));
        chat.setCreatorId(resultSet.getInt("creator_id"));
        chat.setChatName(resultSet.getString("chat_name"));
        chat.setChatTopic(resultSet.getString("chat_topic"));
        chat.setDate(resultSet.getLong("creating_date"));
        return chat;
    }
}
