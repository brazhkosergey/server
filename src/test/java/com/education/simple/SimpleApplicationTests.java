package com.education.simple;

import com.education.simple.DAO.interfaces.ChatsRepository;
import com.education.simple.DAO.interfaces.MessageRepository;
import com.education.simple.DAO.interfaces.UserRepository;
import com.education.simple.entity.Chat;
import com.education.simple.entity.Message;
import com.education.simple.entity.User;
import com.education.simple.enums.FriendStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleApplicationTests {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserRepository userService;
    @Autowired
    ChatsRepository chatService;
    @Autowired
    MessageRepository messageService;

    private Random random = new Random();

    //    @Before
    public void dropTables() {
        String dropTablesString = "drop table if exists friends";
        jdbcTemplate.update(dropTablesString);
        dropTablesString = "drop table if exists users";
        jdbcTemplate.update(dropTablesString);
        dropTablesString = "drop table if exists chats";
        jdbcTemplate.update(dropTablesString);
        dropTablesString = "drop table if exists chats_users";
        jdbcTemplate.update(dropTablesString);
        dropTablesString = "drop table if exists messages";
        jdbcTemplate.update(dropTablesString);
        int show_tables = jdbcTemplate.update("show tables");
        assert show_tables == 0;

        String createUserTable = "create table users( " +
                "id int not null auto_increment," +
                "name varchar(50) not null," +
                "second_name varchar(50)," +
                "email varchar(100) not null," +
                "password varchar(25) not null," +
                "registration_date bigint not null," +
                "primary key(id))";
        jdbcTemplate.update(createUserTable);

        String createFriendsTable = "create table friends(" +
                "user_id int not null," +
                "status_friends enum('friend', 'partner') not null," +
                "status_learning enum('none','teacher', 'student') not null default 'none'," +
                "friend_id int not null references users(id) on delete cascade," +
                "approved bit not null default 0," +
                "rejected bit not null default 0," +
                "primary key (user_id, friend_id)," +
                "foreign key (user_id) references users(id) on delete cascade," +
                "foreign key (friend_id) references users(id) on delete cascade)";
        jdbcTemplate.update(createFriendsTable);

        String createChatsTable = "create table chats(" +
                "id int not null auto_increment," +
                "creator_id int not null references users(id) on delete cascade," +
                "chat_name varchar(100)," +
                "chat_topic varchar(100)," +
                "creating_date int not null," +
                "primary key(id))";
        jdbcTemplate.update(createChatsTable);

        String createChatsUsersTable = "create table chats_users(" +
                "chat_id int not null references chats(id) on delete cascade," +
                "user_id int not null references users(id) on delete cascade," +
                "primary key(chat_id))";
        jdbcTemplate.update(createChatsUsersTable);

        String createMessagesTable = "create table messages(" +
                "id int not null auto_increment," +
                "maker_id int not null references users(id) on delete cascade," +
                "receiver_id int references users(id)," +
                "chat_id int not null references chats(id) on delete cascade," +
                "text_message text," +
                "time_message bigint," +
                "was_read bit not null default 0," +
                "primary key(id))";
        jdbcTemplate.update(createMessagesTable);

        show_tables = jdbcTemplate.update("show tables");

        System.out.println("Tables was created " + show_tables);
        assert show_tables == 5;
    }


    @Test
    public void createChatTest() {
        List<User> allUsers = userService.getAllUsers();
        User userChatMaker = allUsers.get(random.nextInt(allUsers.size()));
        User user1 = allUsers.get(random.nextInt(allUsers.size()));
        User user2 = allUsers.get(random.nextInt(allUsers.size()));
        User user3 = allUsers.get(random.nextInt(allUsers.size()));

        System.out.println("Creator " + userChatMaker.getName() + ". Id " + userChatMaker.getId());
        System.out.println("User 1 - " + user1.getName());
        System.out.println("User 2 - " + user2.getName());
        System.out.println("User 3 - " + user3.getName());

        Chat chat = userChatMaker.createChat();
        chat.setChatName("User Chat maker");
        chatService.saveChat(chat);
        chatService.addUserToChat(chat.getId(), user1.getId());
        chatService.addUserToChat(chat.getId(), user2.getId());
        chatService.addUserToChat(chat.getId(), user3.getId());

        System.out.println("Chats ");
        List<Chat> allChatsByUser = chatService.getAllChatsByUser(userChatMaker.getId());

        System.out.println("Count of chats - " + allChatsByUser.size());
        for (Chat ch : allChatsByUser) {
            System.out.println("Name " + ch.getChatName());
            List<User> usersId = chatService.getUsersFromChat(ch.getId());
            System.out.println("USERS");
            for (User id : usersId) {
                System.out.println("Name of User " + id.getName());
            }
        }

        chatService.deleteChat(chat.getId());
    }


    @Test
    public void createMessageTest() {
        Chat chat = chatService.getAllChatsByUser(22).get(0);
        List<User> usersFromChat = chatService.getUsersFromChat(chat.getId());
        for (int i = 0; i < 20; i++) {
            int firstUser = random.nextInt(usersFromChat.size());
            int secondUser = random.nextInt(usersFromChat.size());
            while (secondUser == firstUser) {
                secondUser = random.nextInt(usersFromChat.size());
            }

            User userFrom = usersFromChat.get(firstUser);
            User userTo = usersFromChat.get(secondUser);

            Message message = chat.createMessage(userFrom, userTo, "Text message from user " + userFrom.getName() + " to user " + userTo.getName());
            messageService.saveMessage(message);

            if (random.nextBoolean()) {
                message.setWasRead(true);
                messageService.setMessageRead(message);
            }

            if (random.nextBoolean()) {

                System.out.println("Message will be deleted "  +message.getId());
                messageService.deleteMessage(message.getId());
            }
        }
        chatService.deleteChat(chat.getId());
    }

    //    @Test
    public void createUsersTest() {
        for (int i = 1; i < 31; i++) {
            User user = new User("User number " + i, "email " + i, "password " + i);
            userService.saveUser(user);
        }
        List<User> allUsers = userService.getAllUsers();
        System.out.println("Users was created " + allUsers.size());
        assert allUsers.size() == 30;

        for (User user : allUsers) {
            for (int k = 0; k < 10; k++) {
                User friend = allUsers.get(random.nextInt(allUsers.size()));
                if (friend != user) {
                    FriendStatus friendStatus;
                    if (k % 2 == 0) {
                        friendStatus = FriendStatus.friend;
                    } else {
                        friendStatus = FriendStatus.partner;
                    }
                    userService.addFriendToUser(user, friend, friendStatus);
                } else {
                    k--;
                }
            }
        }

        User user = allUsers.get(0);

        List<User> friendsByUser = userService.getFriendsByUser(user.getId());
        System.out.println("Total friends - " + friendsByUser.size());
        assert friendsByUser.size() < 10;

        System.out.println("Friends - " + userService.getFriendsByUser(user.getId(), FriendStatus.friend).size());
        assert userService.getFriendsByUser(user.getId(), FriendStatus.friend).size() < 5;

        System.out.println("Partner - " + userService.getFriendsByUser(user.getId(), FriendStatus.partner).size());
        assert userService.getFriendsByUser(user.getId(), FriendStatus.partner).size() < 5;
    }


//    @Test
//    public void addFriendsTest() {
//        List<User> allUsers = userService.getAllUsers();
//        for (User user : allUsers) {
//            for (int k = 0; k < 10; k++) {
//                User friend = allUsers.get(random.nextInt(allUsers.size()));
//                if (friend != user) {
//                    FriendStatus friendStatus;
//                    if (k % 2 == 0) {
//                        friendStatus = FriendStatus.friend;
//                    } else {
//                        friendStatus = FriendStatus.partner;
//                    }
//                    userService.addFriendToUser(user, friend, friendStatus);
//                } else {
//                    k--;
//                }
//            }
//        }
//
//        User user = allUsers.get(0);
//
//        List<User> friendsByUser = userService.getFriendsByUser(user.getId());
////        assert friendsByUser.size() == 10;
//        assert friendsByUser.size() == 8;
//
//
////        assert userService.getFriendsByUser(user.getId(), FriendStatus.friend).size() == 4;
////        assert userService.getFriendsByUser(user.getId(), FriendStatus.partner).size() == 3;
//
////        for (User user : allUsers) {
////            List<User> friendsByUser = userService.getFriendsByUser(user.getId());
////            assert friendsByUser.size() == 10;
////            assert userService.getFriendsByUser(user.getId(), FriendStatus.friend).size() == 4;
////            assert userService.getFriendsByUser(user.getId(), FriendStatus.partner).size() == 3;
////        }
//    }


//    @Test
//    public void contextLoads() {
//
//    }
}
