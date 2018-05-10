package com.nchu.wechatOrder.controller;

import com.nchu.wechatOrder.service.ISellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

    @Autowired
    private ISellService sellService;


    /**
     * 进入登陆页面
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {

        return "/admin/login";
    }




}
