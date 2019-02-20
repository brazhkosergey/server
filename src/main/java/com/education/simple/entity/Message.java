package com.education.simple.entity;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Message implements Entity {
    private int id;
    private int makerId;
    private int receiverId;
    private int chatId;
    private String textMessage;
    private long timeMessage;
    private boolean wasRead;

    public Message() {
    }


    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getId() {
        return id;
    }

    public int getMakerId() {
        return makerId;
    }

    public void setMakerId(int makerId) {
        this.makerId = makerId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public long getTimeMessage() {
        return timeMessage;
    }

    public void setTimeMessage(long timeMessage) {
        this.timeMessage = timeMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getId() == message.getId() &&
                getMakerId() == message.getMakerId() &&
                getReceiverId() == message.getReceiverId() &&
                getChatId() == message.getChatId() &&
                getTimeMessage() == message.getTimeMessage() &&
                Objects.equals(getTextMessage(), message.getTextMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMakerId(), getReceiverId(), getChatId(), getTextMessage(), getTimeMessage());
    }

    public boolean isWasRead() {
        return wasRead;
    }

    public void setWasRead(boolean wasRead) {
        this.wasRead = wasRead;
    }

    @Override
    public void setId(int id) {
        this.id=id;
    }
}
