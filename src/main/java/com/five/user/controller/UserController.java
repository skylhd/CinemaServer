package com.five.user.controller;

import com.five.user.model.MyMessage;
import com.five.user.model.User;
import com.five.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by haoye on 17-6-6.
 */
@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public MyMessage register(User user) {
        MyMessage message = userService.doRegister(user);
        return message;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public MyMessage login(String username, String password, HttpSession session) {
        MyMessage message = userService.doLogin(username, password, session);
        System.out.println(session.getAttribute("userId"));
        return message;
    }

}
