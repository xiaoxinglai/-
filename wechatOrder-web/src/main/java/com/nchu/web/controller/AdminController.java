package com.nchu.web.controller;

import com.nchu.pojo.DO.SellerInfo;
import com.nchu.common.Enum.UserEnum;
import com.nchu.common.Result.BizResult;
import com.nchu.mapper.ex.SellerInfoMapperEx;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nchu.product.service.ISellService;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private ISellService sellService;
    @Autowired
    private SellerInfoMapperEx sellerInfoMapper;


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
     * 进入超级管理员页面
     *
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/AdminUSer", method = RequestMethod.GET)
    public String AdminUSer(HttpSession session) {

        SellerInfo user = (SellerInfo) session.getAttribute("User");
        if (user==null||user.getState()!= UserEnum.ADMIN.getCode().byteValue()){
            return "redirect:login";
        }

        return "admin/AdminUser";
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
                return "redirect:/AdminUSer";
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


    /**
     * 查询所有商家的信息带分页
     */
    @RequestMapping(value = "/querySellBypage", method = RequestMethod.GET)
    @ResponseBody
    public Object querySellBypage(HttpSession session) {

        SellerInfo user = (SellerInfo) session.getAttribute("User");
        if (user==null||user.getState()!=UserEnum.ADMIN.getCode().byteValue()){
            return "redirect:login";
        }


        return sellService.querySellBypage(UserEnum.USER.getCode().byteValue());

    }

    /**
     * 更改商家状态
     */
    @RequestMapping(value = "/changeState", method = RequestMethod.GET)
    @ResponseBody
    public Integer changeState(@Param("SellId") String SellId,@Param("State") Integer State, HttpSession session) {

        SellerInfo user = (SellerInfo) session.getAttribute("User");
        if (user==null||user.getState()!=UserEnum.ADMIN.getCode().byteValue()){
            return -1;
        }


       SellerInfo sellerInfo= sellerInfoMapper.selectByPrimaryKey(SellId);
        sellerInfo.setState(State.byteValue());
        return  sellerInfoMapper.updateByPrimaryKey(sellerInfo);

    }

}
