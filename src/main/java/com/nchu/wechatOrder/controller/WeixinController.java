package com.nchu.wechatOrder.controller;


import com.google.gson.Gson;
import com.nchu.wechatOrder.domain.Form.OpenId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/weixin")
public class WeixinController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WeixinController.class);
    Gson gson = new Gson();

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code, @RequestParam("state") String state) {
        log.info("进入auth方法");
        log.info("code={}", code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxc364d1d3f0307377&secret=2498aaa82614f5c7379c88f3b1553a05&code=" + code + "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);

        //字符串反序列化
       OpenId openId= gson.fromJson(response,OpenId.class);
        log.info("openId={}", openId.getOpenid());
        log.info("state={}", state);
    }


}
