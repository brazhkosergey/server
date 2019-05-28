package com.education.simple.controller.userControllers;

import com.education.simple.DAO.interfaces.UserRepository;
import com.education.simple.entity.User;
import com.education.simple.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserRegistrationController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView creteNewUser(User user, ModelAndView modelAndView) {
        user.setRole(Role.user);
        userRepository.saveUser(user);
        modelAndView.addObject("user", user);
        modelAndView.addObject("done", true);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
