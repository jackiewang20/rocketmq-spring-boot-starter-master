package com.example.rocketmq.producer.controller;

import com.example.rocketmq.producer.bean.User;
import com.example.rocketmq.producer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@RequestMapping("user")
@Controller
public class UserController {
    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @RequestMapping("/findbyid")
    public @ResponseBody
    User findById(Integer id) throws Exception {
        LOGGER.debug("[info]" + new Date().toString());
        LOGGER.info("[info]" + new Date().toString());
        LOGGER.warn("[warning]" + new Date().toString());
        LOGGER.error("[error]" + new Date().toString());
        return userService.findById(id);
    }

}
