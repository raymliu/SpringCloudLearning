package com.forezp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ray Ma on 2019/9/19.
 */
@Configuration
public class RabbitMQConfiguration {

    @Bean
    public Queue Queue1() {
        return new Queue("rayTest1");
    }

    @Bean
    public Queue Queue2() {
        return new Queue("rayTest2");
    }

}
