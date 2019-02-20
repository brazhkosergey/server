package com.education.simple.DAO.interfaces;

import com.education.simple.entity.User;
import com.education.simple.enums.FriendStatus;

import java.util.List;

public interface UserRepository {
    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(String userId);

    User getUserById(long id);

    User getUserByIdEmail(String email);

    List<User> getAllUsers();

    List<User> getFriendsByUser(long userId);

    List<User> getFriendsByUser(long userId, FriendStatus friendStatus);

    void addFriendToUser(User user, User newFriend, FriendStatus friendStatus);

    void approveFriend(User user, User newFriend, boolean approve);
}
