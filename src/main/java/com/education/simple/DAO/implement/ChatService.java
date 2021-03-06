package com.education.simple.DAO.implement;

import com.education.simple.DAO.StaticService;
import com.education.simple.DAO.interfaces.ChatsRepository;
import com.education.simple.DAO.interfaces.UserRepository;
import com.education.simple.DAO.mapper.ChatMapper;
import com.education.simple.DAO.mapper.MessageMapper;
import com.education.simple.DAO.mapper.UserMapper;
import com.education.simple.entity.Chat;
import com.education.simple.entity.Message;
import com.education.simple.entity.User;
import com.education.simple.enums.LearningStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ChatService implements ChatsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserRepository userRepository;

    public ChatService() {
    }

    public ChatService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveChat(Chat chat) {
        final PreparedStatementCreator psc = connection -> {
            final PreparedStatement ps = connection.prepareStatement("insert INTO chats (creator_id," +
                            "chat_name, chat_topic, creating_date)" +
                            " VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, chat.getCreatorId());
            ps.setString(2, chat.getChatName());
            ps.setString(3, chat.getChatTopic());
            ps.setLong(4, chat.getDate());
            return ps;
        };
        StaticService.setIdToEntity(jdbcTemplate, psc, chat);
    }

    @Override
    public Chat getChatByUsers(List<Integer> userdId) {

        return null;
    }


    @Override
    public Chat getChatForTwoUsers(int firstUser, int secondUser) {
        Chat chat = null;
        String SQL = "SELECT * from chats where id = (select chat_id from chats_users where user_id = ? or user_id=? group by(chat_id) having count(chat_id)=2)";
        List<Chat> query = jdbcTemplate.query(SQL, new ChatMapper(), firstUser, secondUser);
        if (query.size() != 0) {
            chat = query.get(0);
        }
        return chat;
    }

    @Override
    public void addUserToChat(int chatId, int userId) {
        String checkSql = "select * from chats_users where chat_id = ? and user_id=?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(checkSql, chatId, userId);
        if (maps.size() == 0) {
            String SQL = "insert into chats_users (chat_id,user_id) values(?,?)";
            jdbcTemplate.update(SQL, chatId, userId);
        }
    }

    @Override
    public void deleteChat(int chatId) {
        String sql = "delete from chats where id = ?";
        jdbcTemplate.update(sql, chatId);
    }

    @Override
    public List<Chat> getAllChatsByUser(int userId) {
        String sql = "select * from chats where creator_id = ?";
        List<Chat> query = jdbcTemplate.query(sql, new ChatMapper(), userId);
        return query;
    }

    @Override
    public List<User> getUsersFromChat(int chatId) {
        String sql = "SELECT  * from users where id in (select user_id from chats_users where chat_id = ?)";
        return jdbcTemplate.query(sql, new UserMapper(), chatId);
    }

    @Override
    public List<Message> getAllMessageFromChat(int chatId) {
        String sql = "select * from messages where chat_id=?";
        return jdbcTemplate.query(sql, new MessageMapper(), chatId);
    }
}
