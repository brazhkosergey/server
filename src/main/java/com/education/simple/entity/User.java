package com.education.simple.entity;

import com.education.simple.DAO.interfaces.ChatsRepository;
import com.education.simple.enums.EntityType;
import com.education.simple.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Component
@Scope(value = "prototype")
public class User implements Entity,HttpEntity {
    Random random  = new Random();

    private int id;
    private Role role;
    private String name;
    private String secondName;
    private String mailAddress;
    private String password;
    private long registrationDate;
    private long dateOfBirth;



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

    @Override
    public EntityType getEntityType() {
        return null;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return mailAddress;
    }

    @Override
    public int getLikesCount() {
        return random.nextInt(1000);
    }

    @Override
    public int getViewsCount() {

        return random.nextInt(1000);
    }

    @Override
    public int getCommentsCount() {
        return random.nextInt(1000);
    }

    @Override
    public String getImagePath() {
        return "/images/news/training_1.jpeg";
    }

    @Override
    public String getFirstButtonName() {
        return null;
    }

    @Override
    public String getSecondButtonName() {
        return null;
    }

    @Override
    public String getThirdButtonName() {
        return null;
    }

    @Override
    public String getFourthButtonName() {
        return null;
    }

    @Override
    public String getFifthButtonName() {
        return null;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
