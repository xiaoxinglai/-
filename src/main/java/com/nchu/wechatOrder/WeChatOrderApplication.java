package com.nchu.wechatOrder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
@EnableRedisHttpSession
@ComponentScan("com.nchu")
public class WeChatOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeChatOrderApplication.class, args);
    }
}
