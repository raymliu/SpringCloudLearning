package com.forezp;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by Ray Ma on 2019/9/19.
 */
@Component
@RabbitListener(queues = "rayTest2")
public class RabbitMqConsumer2 {



    @RabbitHandler
    public void resceiveMessage2(String message){
        System.out.println("Test2 receiver2:"+message);

    }
}
