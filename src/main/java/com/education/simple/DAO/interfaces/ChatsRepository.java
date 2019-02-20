package com.education.simple.DAO.interfaces;

import com.education.simple.entity.Chat;
import com.education.simple.entity.Message;
import com.education.simple.entity.User;

import java.util.List;

public interface ChatsRepository { //словарь матов
    void saveChat(Chat chat);
    void deleteChat(long chatId);
    void addUserToChat(long chatId,long userId);
    List<Chat> getAllChatsByUser(long userId);
    List<User> getUsersFromChat(Long chatId);
    List<Message> getAllMessageFromChat(Long chatId);
}
