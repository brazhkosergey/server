package com.education.simple.DAO.interfaces;

import com.education.simple.entity.Message;

import java.util.List;

public interface MessageRepository {
    void saveMessage(Message message);
    void setMessageRead(Message messageRead);
    void deleteMessage(int messageId);



    List<Message> getNewMessage(int userId);
    List<Message> getMessageByChatFromDateToDate(int chatId,Long dateFrom, Long dateTo);
}
