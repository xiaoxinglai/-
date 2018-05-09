package com.nchu.wechatOrder.controller;

import com.nchu.wechatOrder.domain.Result.Result;
import com.nchu.wechatOrder.domain.VO.BaseInfo;
import com.nchu.wechatOrder.domain.VO.PageResult;
import com.nchu.wechatOrder.domain.VO.SureOrderVO;
import com.nchu.wechatOrder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 处理加入购物车
     * @return
     */
    @RequestMapping(value = "/OrderCar",method =RequestMethod.POST)
    @ResponseBody
    public  Integer  OrderCar(@RequestBody Map<String,Integer> OrderMap, HttpSession session){

        BaseInfo baseInfo=(BaseInfo)session.getAttribute("baseInfo");

        Result result=orderService.toOrderCar(OrderMap,baseInfo);


        //加入购物车成功则返回1 否则返回-1
        if (result.getSuccess()){
            return 1;
        }else {
            return -1;
        }

    }


    //进入购物车页面
    @RequestMapping(value = "/orderCar", method = RequestMethod.GET)
    public String Order(Model model) {


        return "/OrderCar";
    }

    //进入订单页面
    @RequestMapping(value = "/UserOrder", method = RequestMethod.GET)
    public String UserOrder(Model model) {


        return "/UserOrder";
    }


    //进入商家已接单页面
    @RequestMapping(value = "/CompletOrderCar", method = RequestMethod.GET)
    public String CompletOrderCar(Model model) {


        return "/CompletOrderCar";
    }


    //进入待退款订单页面
    @RequestMapping(value = "/ApplyPayBackOrder", method = RequestMethod.GET)
    public String ApplyPayBackOrder(Model model) {


        return "/ApplyPayBackOrder";
    }

    //进入已退款订单页面
    @RequestMapping(value = "/PayBackOrder", method = RequestMethod.GET)
    public String PayBackOrder(Model model) {


        return "/PayBackOrder";
    }


    //获取指定用户的购物车信息
    @RequestMapping(value = "/orderCar/User", method = RequestMethod.GET)
    @ResponseBody
    public Object OrderCarByUser(@RequestParam(value = "currPage", required=false) Integer currPage,HttpSession session) {

        if (ObjectUtils.isEmpty(currPage)){
            currPage=1;
        }
        BaseInfo baseInfo=(BaseInfo)session.getAttribute("baseInfo");
       // BaseInfo baseInfo=new BaseInfo("一楼2号桌","abcdef","1");

        PageResult pageResult=orderService.queryOrderCarByUser(currPage,baseInfo);

        return pageResult;
    }

    //获取指定用户的订单信息
    @RequestMapping(value = "/UserOrderCenter", method = RequestMethod.GET)
    @ResponseBody
    public Object UserOrderCenter(@RequestParam(value = "currPage", required=false) Integer currPage,HttpSession session) {

        if (ObjectUtils.isEmpty(currPage)){
            currPage=1;
        }
        BaseInfo baseInfo=(BaseInfo)session.getAttribute("baseInfo");
        // BaseInfo baseInfo=new BaseInfo("一楼2号桌","abcdef","1");

        PageResult pageResult=orderService.queryUserOrderByUser(currPage,baseInfo);

        return pageResult;
    }

    //获取指定用户的待退款订单信息
    @RequestMapping(value = "/queryapplyPayBack", method = RequestMethod.GET)
    @ResponseBody
    public Object queryapplyPayBack(@RequestParam(value = "currPage", required=false) Integer currPage,HttpSession session) {

        if (ObjectUtils.isEmpty(currPage)){
            currPage=1;
        }
        BaseInfo baseInfo=(BaseInfo)session.getAttribute("baseInfo");
        // BaseInfo baseInfo=new BaseInfo("一楼2号桌","abcdef","1");

        PageResult pageResult=orderService.queryapplyPayBack(currPage,baseInfo);

        return pageResult;
    }

    //获取指定用户的商家已经确认接单信息列表
    @RequestMapping(value = "/SureOrder", method = RequestMethod.GET)
    @ResponseBody
    public Object SureOrder(@RequestParam(value = "currPage", required=false) Integer currPage,HttpSession session) {

        if (ObjectUtils.isEmpty(currPage)){
            currPage=1;
        }
        BaseInfo baseInfo=(BaseInfo)session.getAttribute("baseInfo");
        // BaseInfo baseInfo=new BaseInfo("一楼2号桌","abcdef","1");

        PageResult pageResult=orderService.QuerySureOrder(currPage,baseInfo);

        return pageResult;
    }


    //获取指定用户的已退款完成订单信息
    @RequestMapping(value = "/queryPayBack", method = RequestMethod.GET)
    @ResponseBody
    public Object queryPayBack(@RequestParam(value = "currPage", required=false) Integer currPage,HttpSession session) {

        if (ObjectUtils.isEmpty(currPage)){
            currPage=1;
        }
        BaseInfo baseInfo=(BaseInfo)session.getAttribute("baseInfo");
        // BaseInfo baseInfo=new BaseInfo("一楼2号桌","abcdef","1");

        PageResult pageResult=orderService.QueryPayBack(currPage,baseInfo);

        return pageResult;
    }



    //取消订单
    @RequestMapping(value = "/orderCar/Cancel", method = RequestMethod.GET)
    @ResponseBody
    public Integer CancelOrderCarByUser(@RequestParam(value = "OrderId", required=false) String OrderId,HttpSession session) {



       // BaseInfo baseInfo=(BaseInfo)session.getAttribute("baseInfo");

        Result result=orderService.CancelOrderCar(OrderId);

        //加入取消成功则返回1 否则返回-1
        if (result.getSuccess()){
            return 1;
        }else {
            return -1;
        }
    }


    //申请退款
    @RequestMapping(value = "/applyPayBack", method = RequestMethod.GET)
    @ResponseBody
    public Integer applyPayBack(@RequestParam(value = "OrderId", required=false) String OrderId,HttpSession session) {

        // BaseInfo baseInfo=(BaseInfo)session.getAttribute("baseInfo");

        Result result=orderService.applyPayBack(OrderId);

        //成功则返回1 否则返回-1
        if (result.getSuccess()){
            return 1;
        }else {
            return -1;
        }
    }


    //取消申请退款
    @RequestMapping(value = "/CancelapplyPayBack", method = RequestMethod.GET)
    @ResponseBody
    public Integer CancelapplyPayBack(@RequestParam(value = "OrderId", required=false) String OrderId,HttpSession session) {

        // BaseInfo baseInfo=(BaseInfo)session.getAttribute("baseInfo");

        Result result=orderService.CancelapplyPayBack(OrderId);

        //成功则返回1 否则返回-1
        if (result.getSuccess()){
            return 1;
        }else {
            return -1;
        }
    }


    /**
     * 确认下单
     * @return
     */
    @RequestMapping(value = "/SureOrder",method =RequestMethod.POST)
    @ResponseBody
    public  int  SureOrder(@RequestBody SureOrderVO sureOrderVO, HttpSession session){

        BaseInfo baseInfo=(BaseInfo)session.getAttribute("baseInfo");

        Result result=orderService.SureOrder(sureOrderVO);


        //确认下单成功则返回1 否则返回-1
        if (result.getSuccess()){
            return 1;
        }else {
            return -1;
        }





    }




}
