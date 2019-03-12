package com.education.simple.controller;

import com.education.simple.DAO.interfaces.ChatsRepository;
import com.education.simple.DAO.interfaces.MessageRepository;
import com.education.simple.DAO.interfaces.UserRepository;
import com.education.simple.entity.User;
import com.education.simple.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    ApplicationContext context;
    @Autowired
    private UserRepository userService;
    @Autowired
    private ChatsRepository chatsService;
    @Autowired
    private MessageRepository messageService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView registration(ModelAndView modelAndView,
                                     Principal principal) {
        User user = userService.getUserByIdEmail(principal.getName());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login(ModelAndView modelAndView, Principal principal) {
        if (principal == null) {
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("index");
        } else {
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }
}
