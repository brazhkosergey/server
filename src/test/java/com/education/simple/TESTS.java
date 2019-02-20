package com.education.simple;

import com.education.simple.DAO.implement.ChatService;
import com.education.simple.DAO.implement.UserService;
import com.education.simple.entity.Chat;
import com.education.simple.entity.User;
import com.education.simple.enums.FriendStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.persistence.criteria.CriteriaBuilder;
import javax.sql.DataSource;
import java.util.List;
import java.util.Random;

public class TESTS {

    static Random random = new Random();

    public static void main(String[] args) {
        DataSource dataSource = getDataSource();
        UserService userService = new UserService(new JdbcTemplate(dataSource));
        ChatService chatService = new ChatService(new JdbcTemplate(dataSource));

//        createUsersAndFriends(userService);
//        chatCreationTest(chatService,userService);



    }


    static void chatCreationTest(ChatService chatService,UserService userService){
        List<User> allUsers = userService.getAllUsers();
        User userChatMaker = allUsers.get(random.nextInt(allUsers.size()));
        User user1 = allUsers.get(random.nextInt(allUsers.size()));
        User user2 = allUsers.get(random.nextInt(allUsers.size()));
        User user3 = allUsers.get(random.nextInt(allUsers.size()));

        Chat chat = userChatMaker.createChat();
        chat.setChatName("User Chat maker");
        chat.addUserToChat(user1);
        chat.addUserToChat(user2);
        chat.addUserToChat(user3);

        List<Chat> allChatsByUser = chatService.getAllChatsByUser(userChatMaker.getId());
        System.out.println("Chats ");
        for(Chat ch:allChatsByUser){
            System.out.println("Name "+ch.getChatName());
            List<Integer> usersId = ch.getUsersId();
            System.out.println("USERS");
            for(Integer id:usersId){
                System.out.println("Name of User "+userService.getUserById(id).getName());
            }
        }

    }

    static void createUsersAndFriends(UserService service) {
        Random random = new Random();
        for (int i = 1; i < 31; i++) {
            User user = new User("User number " + i, "email " + i, "password " + i);
            service.saveUser(user);
        }
        List<User> allUsers = service.getAllUsers();
        for (User user : allUsers) {
            for (int k = 0; k < 10; k++) {
                User friend = allUsers.get(random.nextInt(allUsers.size()));
                if (friend != user) {
                    FriendStatus friendStatus;
                    if (random.nextBoolean()) {
                        friendStatus = FriendStatus.friend;
                    } else {
                        friendStatus = FriendStatus.partner;
                    }
                    service.addFriendToUser(user, friend, friendStatus);
                }
            }
        }

        List<User> users = service.getAllUsers();
        for (User user : users) {
            System.out.println("Name " + user.getName() + ". Second name " + user.getSecondName());
            System.out.println("FRIENDS");
            List<User> friendsByUser = service.getFriendsByUser(user.getId(), FriendStatus.friend);
            for (User u : friendsByUser) {
                System.out.println("friend - " + u.getName());
            }
            System.out.println("PARTNERS");
            friendsByUser = service.getFriendsByUser(user.getId(), FriendStatus.partner);
            for (User u : friendsByUser) {
                System.out.println("friend - " + u.getName());
            }
            System.out.println("=========================================================");
        }
    }




    static DataSource getDataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/test");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("root");
        return driverManagerDataSource;
    }
}
