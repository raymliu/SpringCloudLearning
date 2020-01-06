package com.forezp.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ray Ma on 2019/9/19.
 */
@RestController
@RequestMapping("/waihui")
public class TestController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostMapping("/test")
    public Tip sendTest() {

        String result = null;

        String host = "http://alirm-gbfsb.konpn.com";
        String path = "/query/comrms";
        String method = "GET";
        String appcode = "35372155ee284bada1409bf2a80c353e";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("symbols", "USDX美元指数,EURUSD,BTCUSD,GBPUSD,EURJPY");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());

             result = EntityUtils.toString(response.getEntity());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject parse = (JSONObject)JSONObject.parse(result);
        JSONArray jsonObject = (JSONArray) parse.get("Obj");


        return new SuccessTip(jsonObject, "获取成功");

    }
}


