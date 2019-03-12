package com.education.simple;

import com.education.simple.DAO.interfaces.ChatsRepository;
import com.education.simple.DAO.interfaces.MessageRepository;
import com.education.simple.DAO.interfaces.UserRepository;
import com.education.simple.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class MockTests {

    @Mock
    JdbcTemplate jdbcTemplate;
    @Mock
    UserRepository userService;
    @Mock
    ChatsRepository chatService;
    @Mock
    MessageRepository messageService;

    @Before
    public void setUp() {
        initMocks(this);




    }

    @Test
    public void createUserTest() {
        for (int i = 1; i < 31; i++) {
            User user = new User("User number " + i, "email " + i + "@test.com", "password " + i);
            userService.saveUser(user);
        }

        System.out.println("SSLLLEEEEPPPPp");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<User> allUsers = userService.getAllUsers();
        assertEquals(30, allUsers.size());
    }
}
