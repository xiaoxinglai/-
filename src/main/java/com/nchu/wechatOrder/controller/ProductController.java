package com.nchu.wechatOrder.controller;

import com.nchu.wechatOrder.domain.VO.PageResult;
import com.nchu.wechatOrder.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private IProductService productService;

    /**
     * 查询所有菜品
     * @param currPage
     * @param SellId
     * @param model
     * @return
     */
    @RequestMapping(value = "/queryProduct",method = RequestMethod.GET)
    @ResponseBody
    public Object queryProduct(@RequestParam(value = "currPage", required=false) Integer currPage,@RequestParam("SellId") String SellId, Model model) {

        if (ObjectUtils.isEmpty(currPage)){
            currPage=1;
        }

        PageResult pageResult=productService.queryProductInfoBysellId(currPage,SellId);
        return pageResult;

    }

}
