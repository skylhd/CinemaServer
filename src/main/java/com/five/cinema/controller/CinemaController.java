package com.five.cinema.controller;

import com.five.cinema.service.CinemaService;
import com.five.user.model.MyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by msi on 2017/6/6.
 */
@RestController
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @PostMapping("/cinema")
    public MyMessage findCinema(int citycode, double longtitude, double latitude, HttpSession session) {
        int currentpage = 0;
        if (session.getAttribute("page") != null) currentpage = Integer.parseInt(session.getAttribute("page").toString());
        MyMessage message = cinemaService.getCinemas(citycode, longtitude, latitude, currentpage);
//        System.out.println(session.getAttribute("userId"));
        return message;
    }


}