package com.forezp.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Ray Ma on 2019/9/19.
 */
@RestController
public class MqTestController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostMapping("/test/mq")
    public String sendTest(@RequestParam("content")String content){
         content=content+new Date();
        for (int i = 0;i < 10;i++) {
            amqpTemplate.convertAndSend("rayTest1",content);
            amqpTemplate.convertAndSend("rayTest2",content);
        }
        return content;

    }
}
