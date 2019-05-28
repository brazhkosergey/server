package com.education.simple.controller;

import com.education.simple.DAO.interfaces.ChatsRepository;
import com.education.simple.DAO.interfaces.MessageRepository;
import com.education.simple.DAO.interfaces.UserRepository;
import com.education.simple.entity.Chat;
import com.education.simple.entity.HttpEntity;
import com.education.simple.entity.Message;
import com.education.simple.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Controller
public class MainController {

    Random random = new Random();
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
    public ModelAndView login(ModelAndView modelAndView, Principal principal, HttpServletRequest request) {

        System.out.println("Client Address: "+request.getRemoteAddr());
        System.out.println("Local Address: "+request.getLocalAddr());
        System.out.println("--------------------------------");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String s = headerNames.nextElement();
            System.out.println(s +":"+request.getHeader(s));
        }

        if (principal == null) {
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("index");
        } else {
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ModelAndView getNewsTable(ModelAndView modelAndView, User user, Principal principal) {
        modelAndView.setViewName("news/news_table");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/news", method = RequestMethod.POST)
    public ModelAndView getNews(ModelAndView modelAndView) {
        List<List<HttpEntity>> entities = new ArrayList<>();
        List<User> allUsers = userService.getAllUsers();

        for (int i = 0; i < 4; i++) {
            List<HttpEntity> list = new ArrayList<>();
            for (int k = 0; k < 4; k++) {
                list.add(allUsers.get(random.nextInt(allUsers.size())));
            }
            entities.add(list);
        }
        modelAndView.setViewName("news/news");
        modelAndView.addObject("entities", entities);
        return modelAndView;
    }

    @RequestMapping(value = "/user_details", method = RequestMethod.POST)
    public ModelAndView getEntityDetails(@RequestParam("id") int id, ModelAndView modelAndView) {
        User userById = userService.getUserById(id);
        modelAndView.addObject("entity", userById);
        modelAndView.setViewName("entity_details");
        return modelAndView;
    }

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public ModelAndView getChat(@RequestParam("id") int userId, ModelAndView modelAndView) {
        List<User> friends = userService.getFriendsByUser(userId);
        modelAndView.setViewName("chat/chat");
        modelAndView.addObject("friends", friends);
        return modelAndView;
    }

    @RequestMapping(value = "/current_chat", method = RequestMethod.POST)
    public ModelAndView getCurrentChat(@RequestParam("makerId") int makerId,
                                       @RequestParam("receiverId") int receiverId,
                                       @RequestParam("receiverName") String receiverName,
                                       @RequestParam("receiverEmail") String receiverEmail,
                                       ModelAndView modelAndView) {
        Chat chat = chatsService.getChatForTwoUsers(makerId, receiverId);
        if (chat == null) {
            chat = new Chat(makerId);
            chatsService.saveChat(chat);
            chatsService.addUserToChat(chat.getId(), makerId);
            chatsService.addUserToChat(chat.getId(), receiverId);
        }
        long currentTimeMillis = System.currentTimeMillis();
        long oneYearBefore = currentTimeMillis - 1471228928;

        List<Message> messageByChatFromDateToDate = messageService.getMessageByChatFromDateToDate(chat.getId(),
                oneYearBefore,
                currentTimeMillis);
        modelAndView.setViewName("chat/current_chat");
        modelAndView.addObject("chat", chat);
        modelAndView.addObject("userId", makerId);
        modelAndView.addObject("receiverId", receiverId);
        modelAndView.addObject("receiverName", receiverName);
        modelAndView.addObject("receiverEmail", receiverEmail);
        modelAndView.addObject("messages", messageByChatFromDateToDate);
        return modelAndView;
    }


    @RequestMapping(value = "/send_message", method = RequestMethod.POST)
    public @ResponseBody
    int sendMessage(@RequestParam("makerId") int makerId,
                    @RequestParam("receiverId") int receiverId,
                    @RequestParam("chatId") int chatId,
                    @RequestParam("text") String text) {
        Message message = new Message();
        message.setMakerId(makerId);
        message.setReceiverId(receiverId);
        message.setChatId(chatId);
        message.setTextMessage(text);
        message.setTimeMessage(System.currentTimeMillis());
        messageService.saveMessage(message);
        return message.getId();
    }
}
