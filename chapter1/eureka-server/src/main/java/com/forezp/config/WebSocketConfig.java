//package com.forezp.config;
//
//
//import com.forezp.service.WebSocketHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//import javax.annotation.Resource;
//
//
//@Component
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//
//    @Resource
//    private com.forezp.service.WebSocketHandler handler;
//
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
//
//        //这个网址是用于websocket连接的建立 通信用的
//        webSocketHandlerRegistry
//                .addHandler(new WebSocketHandler(), "/waihui")
////                .addInterceptors(new WebSocketInterceptor())
//                .setAllowedOrigins("*");
//    }
//
//    @Bean
//    public WebSocketHandler webSocketHandler(){
//        return new WebSocketHandler();
//    }
//
//
////        webSocketHandlerRegistry.addHandler(handler, "/order").addInterceptors(new HandshakeInterceptor() {
////            @Override
////            public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
////                String userid = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getParameter("userid");
////                String token= ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getParameter("token");
////                String version=((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getParameter("version");
////                String uid="";
////                if (!StringUtils.isNullOrEmpty(userid)&&!StringUtils.isNullOrEmpty(version)&&!StringUtils.isNullOrEmpty(token)) {
////                    try {
////                        //4，rsa私钥获取aeskey
////                        uid= RSA.decrypt(userid,priKey);
////                        //5,aes key 解析加密的sign
//////                        userid= AESECBPKCS7Padding.aes256Decode(userid,asekey);
////                        String tokenKey=String.format(RedisKey.MEMBER_TOKEN,uid);
////                        Object cacheToken= redisCacheDao.get(tokenKey);
////                        if(null==cacheToken ||!cacheToken.toString().equals(token)){
////                            return false;
////                        }
////                        Member member = memberService.selectById(Long.parseLong(uid));
////                        if(null!=member){
////                            map.put("userid", uid);
////                            return true;
////                        }
////                    }catch (Exception e){
////                        LogManager.me().executeLog(LogTaskFactory.loginLog(uid ,"warning login",IPUtil.getIpAddr(((ServletServerHttpRequest) serverHttpRequest).getServletRequest())));
////                    }
////                }
////                return false;
////            }
////            @Override
////            public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
////            }
////        });
////    }
//}
