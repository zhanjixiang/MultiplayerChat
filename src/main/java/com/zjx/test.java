package com.zjx;


import com.alibaba.fastjson.JSON;
import com.zjx.Controller.MassageWebSocket;
import com.zjx.Entity.Friends;
import com.zjx.Service.ChatRoomService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class test {
    private static ConcurrentHashMap<String, String > webSocketMap = new ConcurrentHashMap<String, String >();
    @Test
    public void ttt() {
        webSocketMap.put("123","adf");
        webSocketMap.put("456","aa");
        webSocketMap.put("789","qwe");
        for (String keys : webSocketMap.keySet()){
            System.out.println(keys);
        }

//
//        String message = "123:456:789:10";
//        String msg[] = message.split(":",2);
//        int i = 0;
//        for (String md : msg) {
//            System.out.println("i :" + i + "  " + md);
//            i++;
//        }
    }
}
