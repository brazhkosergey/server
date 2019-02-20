package com.education.simple.controller;


import com.education.simple.DAO.interfaces.ChatsRepository;
import com.education.simple.DAO.interfaces.MessageRepository;
import com.education.simple.DAO.interfaces.UserRepository;
import com.education.simple.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserRepository userService;
    @Autowired
    private ChatsRepository chatsService;
    @Autowired
    private MessageRepository messageService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
//    @RequestMapping("/")
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        System.out.println("REQUEST - index");

        for (int i = 0; i < 10; i++) {
            User user = new User(String.valueOf(i), "email " + i, "password " + i);
            userService.saveUser(user);
        }

        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            Integer integer = Integer.valueOf(user.getName());
            System.out.println("Name of User " + integer);
            if (integer % 2 == 0) {
                userService.deleteUser(String.valueOf(user.getId()));
            } else {
                user.setSecondName("Second name " + integer);
                userService.updateUser(user);
            }
        }
        allUsers = userService.getAllUsers();

        for (User user : allUsers) {
            System.out.println("Name " + user.getName() + ". Second name " + user.getSecondName());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/1", method = RequestMethod.GET)
    public String mainPageOne() {
        System.out.println("REQUEST - registration");
//        Random random = new Random();
//        User user = new User("Vasia " + random.nextInt(), "Dudkin " + random.nextInt());
//        userService.saveUser(user);
        return "indexTest";
    }

    @RequestMapping("/test")
    public @ResponseBody
    String start() {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println("User - " + user.getName() + " / id - " + user.getId());
        }
        return "<h1>OOOOpppssss</h1>";
    }
}
