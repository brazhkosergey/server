package com.education.simple.entity;

import com.education.simple.DAO.interfaces.ChatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Chat implements Entity {

    @Autowired
    ChatsRepository chatService;
    private int id;
    private long date;
    private int creatorId;
    private String chatName;
    private String chatTopic;

    public Chat() {
        date = System.currentTimeMillis();
    }

    public Chat(int creatorId) {
        this();
        this.creatorId = creatorId;
    }

    public Message createMessage(User userFrom, User userTo, String text) {
        Message message = new Message();

        message.setChatId(this.id);
        message.setMakerId(userFrom.getId());
        if (userTo != null) {
            message.setReceiverId(userTo.getId());
        }
        message.setTimeMessage(System.currentTimeMillis());
        message.setTextMessage(text);
        return message;
    }

    public void addUserToChat(User user) {
        chatService.addUserToChat(id, user.getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }


    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatTopic() {
        return chatTopic;
    }

    public void setChatTopic(String chatTopic) {
        this.chatTopic = chatTopic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        Chat chat = (Chat) o;
        return getId() == chat.getId() &&
                getDate() == chat.getDate() &&
                getCreatorId() == chat.getCreatorId() &&
                Objects.equals(chatService, chat.chatService) &&
                Objects.equals(getChatName(), chat.getChatName()) &&
                Objects.equals(getChatTopic(), chat.getChatTopic());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getCreatorId());
    }
}
