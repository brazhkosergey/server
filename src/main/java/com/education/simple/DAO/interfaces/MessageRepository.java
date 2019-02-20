package com.education.simple.DAO.interfaces;

import com.education.simple.entity.Message;

import java.util.List;

public interface MessageRepository {
    void saveMessage(Message message);
    void setMessageRead(Message messageRead);
    void deleteMessage(int messageId);



    List<Message> getNewMessage(Long userId);
    List<Message> getMessageByChatFromDateToDate(Long chatId,Long dateFrom, Long dateTo);
}
