package com.nchu.wechatOrder.controller;

import com.nchu.wechatOrder.domain.DO.SellerInfo;
import com.nchu.wechatOrder.domain.Enum.UserEnum;
import com.nchu.wechatOrder.domain.Result.BizResult;
import com.nchu.wechatOrder.service.ISellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private ISellService sellService;


    /**
     * 进入登陆页面
     *
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {

        return "/admin/login";
    }

    /**
     * 处理登陆请求
     *
     * @param
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/doLogin", method = RequestMethod.POST)
    public String doLogin(SellerInfo UserForm, HttpSession session, Model model) {


        BizResult<SellerInfo> bizResult = sellService.doLogin(UserForm);
        if (bizResult.getSuccess()) {
            session.setAttribute("User", bizResult.getDate());
            SellerInfo resultDate = bizResult.getDate();
            if (resultDate.getState().equals(UserEnum.ADMIN.getCode().byteValue())) {
               return "admin/AdminUser";
            }


            if (resultDate.getState().equals(UserEnum.USER.getCode().byteValue())) {

                //todo 跳转到商家后台
                return "redirect:/AllOrderRecord";
            } else {
                //todo 跳转到管理员后台
                return "redirect:/user/center";
            }

        } else {
            String msg = bizResult.getMsg();
            model.addAttribute("msg", msg);
            //todo 跳转回登陆界面 并提示账号密码有误
            return "/admin/login";
        }

    }


}
