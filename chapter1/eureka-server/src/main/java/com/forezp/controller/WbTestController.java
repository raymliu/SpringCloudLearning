//package com.forezp.controller;
//
//import com.forezp.service.WaiHuiService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import java.io.IOException;
//import java.util.Date;
//
///**
// * Created by Ray Ma on 2019/9/19.
// */
//@RestController
//public class WbTestController {
//    private String userId;
//
//    private Logger logger = LoggerFactory.getLogger(WbTestController.class);
//
//
//    //连接时执行
//    @OnOpen
//    public void onOpen(@PathParam("userId") String userId, Session session) throws IOException {
//        this.userId = userId;
//        logger.debug("新连接：{}",userId);
//    }
//
//    //关闭时执行
//    @OnClose
//    public void onClose(){
//        logger.debug("连接：{} 关闭",this.userId);
//    }
//
//    //收到消息时执行
//    @OnMessage
//    public void onMessage(String message, Session session) throws IOException {
//        logger.debug("收到用户{}的消息{}",this.userId,message);
//        session.getBasicRemote().sendText("收到 "+this.userId+" 的消息 "); //回复用户
//    }
//
//    //连接错误时执行
//    @OnError
//    public void onError(Session session, Throwable error){
//        logger.debug("用户id为：{}的连接发送错误",this.userId);
//        error.printStackTrace();
//    }
//}
