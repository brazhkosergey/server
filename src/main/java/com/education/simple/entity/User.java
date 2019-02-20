package com.education.simple.entity;

import com.education.simple.DAO.interfaces.ChatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class User implements Entity {

    @Autowired
    ChatsRepository chatsRepository;

    private int id;
    private String name;
    private String secondName;
    private String mailAddress;
    private String password;
    private long registrationDate;

    public User() {
        registrationDate = System.currentTimeMillis();
    }

    public User(String name, String mailAddress, String password) {
        this();
        this.name = name;
        this.mailAddress = mailAddress;
        this.password = password;
    }

    public Chat createChat() {
        Chat chat = new Chat(this.id);
        return chat;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(secondName, user.secondName) &&
                Objects.equals(mailAddress, user.mailAddress) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, secondName, mailAddress, password);
    }
}
