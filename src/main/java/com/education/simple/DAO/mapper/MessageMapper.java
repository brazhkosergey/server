package com.education.simple.DAO.mapper;

import com.education.simple.entity.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Message message = new Message();
        message.setId(resultSet.getInt("id"));
        message.setMakerId(resultSet.getInt("maker_id"));
        message.setReceiverId(resultSet.getInt("receiver_id"));
        message.setChatId(resultSet.getInt("chat_id"));
        message.setTextMessage(resultSet.getString("text_message"));
        message.setTimeMessage(resultSet.getLong("time_message"));
        message.setWasRead(resultSet.getBoolean("was_read"));
        return message;
    }
}
