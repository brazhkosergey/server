package com.education.simple.DAO.implement;

import com.education.simple.DAO.StaticService;
import com.education.simple.DAO.interfaces.MessageRepository;
import com.education.simple.DAO.mapper.MessageMapper;
import com.education.simple.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;


@Service
public class MessageService implements MessageRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void saveMessage(Message message) {
        PreparedStatementCreator preparedStatementCreator = connection -> {
            String sql = "insert into messages (maker_id,receiver_id,chat_id," +
                    "text_message,time_message,was_read) values (?,?,?,?,?,?)";
            PreparedStatement p = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, message.getMakerId());
            p.setInt(2, message.getReceiverId());
            p.setInt(3, message.getChatId());
            p.setString(4, message.getTextMessage());
            p.setLong(5, message.getTimeMessage());
            p.setBoolean(6, message.isWasRead());
            return p;
        };
        StaticService.setIdToEntity(jdbcTemplate, preparedStatementCreator, message);
    }

    @Override
    public void setMessageRead(Message messageRead) {
        String sql = "update messages set was_read = ? where id = ?";
        jdbcTemplate.update(sql, messageRead.isWasRead(), messageRead.getId());
    }

    @Override
    public void deleteMessage(int messageId) {
        String sql = "delete from messages where id = ?";
        jdbcTemplate.update(sql, messageId);
    }

    @Override
    public List<Message> getNewMessage(Long userId) {
        String sql = "select * from messages where userId=? and was_read = 0";
        return jdbcTemplate.query(sql, new MessageMapper(), userId);
    }

    @Override
    public List<Message> getMessageByChatFromDateToDate(Long chatId, Long dateFrom, Long dateTo) {
        String sql = "select * from messages where chat_id=? and time_message >  ? and time_message < ?";
        return jdbcTemplate.query(sql, new MessageMapper(), chatId, dateFrom, dateTo);
    }
}
