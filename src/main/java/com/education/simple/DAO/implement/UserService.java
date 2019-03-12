package com.education.simple.DAO.implement;

import com.education.simple.DAO.StaticService;
import com.education.simple.DAO.interfaces.UserRepository;
import com.education.simple.DAO.mapper.UserMapper;
import com.education.simple.entity.User;
import com.education.simple.enums.FriendStatus;
import com.education.simple.enums.LearningStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService implements UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public UserService() {
    }

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveUser(User user) {
        if (getUserByIdEmail(String.valueOf(user.getMailAddress())) == null) {
            final PreparedStatementCreator psc = connection -> {
                final PreparedStatement ps = connection.prepareStatement("insert INTO users (name, second_name, email, password,registration_date,role,date_of_birth)" +
                                " VALUES (?,?,?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getName());
                ps.setString(2, user.getSecondName());
                ps.setString(3, user.getMailAddress());
                ps.setString(4, user.getPassword());
                ps.setLong(5, user.getRegistrationDate());
                ps.setString(6, String.valueOf(user.getRole()));
                ps.setLong(7, user.getDateOfBirth());
                return ps;
            };
            StaticService.setIdToEntity(jdbcTemplate, psc, user);
        }
    }

    @Override
    public void updateUser(User user) {
        String SQL = "update users set name=?,second_name=?,email = ?,password = ?, date_of_birht = ? where id = ?";
        jdbcTemplate.update(SQL, user.getName(), user.getSecondName(), user.getMailAddress(), user.getPassword(), user.getId(),user.getDateOfBirth());
    }

    @Override
    public void deleteUser(String userId) {
        String SQL = "delete from users where id=?";
        jdbcTemplate.update(SQL, userId);
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "Select * from users";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public List<User> getFriendsByUser(long userId) {
        String SQL = "SELECT * from users where id in (select friend_id from friends where user_id = " + userId + ")";
        return jdbcTemplate.query(SQL, new UserMapper());
    }

    @Override
    public List<User> getFriendsByUser(long userId, FriendStatus friendStatus) {
        String SQL = "SELECT * from users where id in (select friend_id from friends where user_id = ? and status_friends = ?)";
        return jdbcTemplate.query(SQL, new UserMapper(), userId, friendStatus.name());
    }

    @Override
    public void addFriendToUser(User user, User newFriend, FriendStatus status) {
        String checkRequest = "select friend_id from friends where user_id = ? and friend_id=?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(checkRequest, user.getId(), newFriend.getId());
        if (maps.size() == 0) {
            String SQL = "INSERT into friends (user_id,status_friends,status_learning,friend_id, approved)" +
                    " values (?,?,?,?,?)";
            jdbcTemplate.update(SQL, user.getId(), status.name(), LearningStatus.none.name(), newFriend.getId(), false);
        }
    }

    @Override
    public void approveFriend(User user, User newFriend, boolean approve) {
        String sql;
        if (approve) {
            sql = "update friends set approved = ? where user_id = ? and friend_id = ?";
        } else {
            sql = "update friends set rejected = ? where user_id = ? and friend_id = ?";
        }
        jdbcTemplate.update(sql, approve, newFriend.getId(), user.getId());
    }

    @Override
    public User getUserById(long id) {
        String SQL = "Select * from users where id = ?";
        List<User> query = jdbcTemplate.query(SQL, new UserMapper(), id);
        User user = null;
        if (query.size() > 0) {
            user = query.get(0);
            String requestForChats;
        }
        return user;
    }

    @Override
    public User getUserByIdEmail(String email) {
        String SQL = "Select * from users where email = ?";
        List<User> query = jdbcTemplate.query(SQL, new UserMapper(), email);
        User user = null;
        if (query.size() > 0) {
            user = query.get(0);
        }
        return user;
    }
}
