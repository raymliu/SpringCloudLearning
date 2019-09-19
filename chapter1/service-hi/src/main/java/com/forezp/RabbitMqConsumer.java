package com.forezp;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by Ray Ma on 2019/9/19.
 */
@Component
@RabbitListener(queues = "lyhTest1")
public class RabbitMqConsumer {



    @RabbitHandler
    public void resceiveMessage(String message){
        System.out.println("Test1 receiver1:"+message);

    }
}
