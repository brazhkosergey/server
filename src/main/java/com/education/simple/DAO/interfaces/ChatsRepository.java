package com.education.simple.DAO.interfaces;

import com.education.simple.entity.Chat;
import com.education.simple.entity.Message;
import com.education.simple.entity.User;
import sun.misc.Cache;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ChatsRepository { //словарь матов
    void saveChat(Chat chat);
    void deleteChat(int chatId);
    void addUserToChat(int chatId,int userId);
    List<Chat> getAllChatsByUser(int userId);
    List<User> getUsersFromChat(int chatId);
    List<Message> getAllMessageFromChat(int chatId);
    Chat getChatByUsers(List<Integer> userdId);
    Chat getChatForTwoUsers(int firstUser,int secondUser);
}
