package com.forezp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ray Ma on 2019/12/9.
 */
@Component
public class WebSocketHandler  extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("Opened new session in instance " + this);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //组装返回的Echo信息
        String echoMessage = message.getPayload();
        logger.debug(MessageFormat.format("Echo message \"{0}\"", message));

        session.sendMessage(new TextMessage(echoMessage));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
        logger.debug("Info: WebSocket connection closed.");
    }



}
