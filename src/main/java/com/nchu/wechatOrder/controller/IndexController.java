package com.nchu.wechatOrder.controller;

import com.nchu.wechatOrder.domain.DO.SellerInfo;
import com.nchu.wechatOrder.domain.VO.BaseInfo;
import com.nchu.wechatOrder.service.ISellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private ISellService sellService;


    /**
     * 进入首页
     * @param SellId
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(@RequestParam("SellId") String SellId, HttpSession session, Model model) {
        SellerInfo sellerInfo=sellService.querySellerInfo(SellId);
        model.addAttribute("sellerInfo",sellerInfo);
        BaseInfo baseInfo=new BaseInfo("一楼2号桌","abcdef","1");
        session.setAttribute("baseInfo",baseInfo);
        return "/index";
    }




}
