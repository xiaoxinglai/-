package com.nchu.wechatOrder.controller;

import com.nchu.wechatOrder.domain.DO.*;
import com.nchu.wechatOrder.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class testController {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private SellerInfoMapper sellerInfoMapper;



    @RequestMapping(value = "/orderDetail",method = RequestMethod.GET)
    @ResponseBody
    public OrderDetail testorderDetail(){
        return orderDetailMapper.selectByPrimaryKey("1");
    }




    @RequestMapping(value = "/orderMaster",method = RequestMethod.GET)
    @ResponseBody
    public OrderMaster orderMaster(){
        return orderMasterMapper.selectByPrimaryKey("1");
    }

    @RequestMapping(value = "/productCategory",method = RequestMethod.GET)
    @ResponseBody
    public ProductCategory productCategory(){
        return productCategoryMapper.selectByPrimaryKey(1);
    }

    @RequestMapping(value = "/productInfo",method = RequestMethod.GET)
    @ResponseBody
    public ProductInfo productInfo(){
        return productInfoMapper.selectByPrimaryKey(1);
    }

    @RequestMapping(value = "/sellerInfo",method = RequestMethod.GET)
    @ResponseBody
    public SellerInfo sellerInfo(){
        return sellerInfoMapper.selectByPrimaryKey("1");
    }
}
