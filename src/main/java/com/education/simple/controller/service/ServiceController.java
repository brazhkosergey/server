package com.education.simple.controller.service;

import com.education.simple.DAO.interfaces.UserRepository;
import com.education.simple.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
    public @ResponseBody
    String checkEmail(@RequestParam("email") String email) {
        String response;
        User userByIdEmail = userRepository.getUserByIdEmail(email);
        if (userByIdEmail == null) {
            response = "false";
        } else {
            if (userByIdEmail.getSecondName() == null) {
                response = userByIdEmail.getName();
            } else {
                response = userByIdEmail.getName() + " " + userByIdEmail.getSecondName();
            }
        }
        return response;
    }
}
