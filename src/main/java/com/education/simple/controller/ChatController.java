package com.education.simple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/app")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SimpUserRegistry simpUserRegistry;


    @MessageMapping("/open_chat")
    @SendToUser()
    public void greeting(Principal principal,
                         String mailAddress,
                         @Payload Message message) {
        simpUserRegistry.getUsers();
        simpMessagingTemplate.convertAndSendToUser(mailAddress, "/queue/reply", message);

//        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue", message);
//        simpMessagingTemplate.convertAndSendToUser(mailAddress, "/queue", message);
//        simpMessagingTemplate.convertAndSendToUser(String.valueOf(message.getHeaders().get("simpSessionId")), "/queue", message);
//        simpMessagingTemplate.convertAndSendToUser("/"+prin.getUsername(), "/queue", message);
//        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue", message);
        //        System.out.println(principal.getName() + " - Send websocket request to user - " + mailAddress +
//                ". Message - " + message.getPayload());
//        System.out.println("======================================================");
//        MessageHeaders headers = message.getHeaders();
//
//        for (String key : headers.keySet()) {
//            System.out.println("Key: " + key + ". Value " + headers.get(key));
//        }
        System.out.println("====================" + mailAddress + "===========" + principal.getName() + "=======================");
    }
}
