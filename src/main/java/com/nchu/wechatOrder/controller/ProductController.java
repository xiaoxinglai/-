package com.nchu.wechatOrder.controller;

import com.nchu.wechatOrder.domain.VO.BaseInfo;
import com.nchu.wechatOrder.domain.VO.PageResult;
import com.nchu.wechatOrder.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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



    /**
     * 查询指定菜品类型的所有菜品
     */
    @RequestMapping(value = "/queryProductByType",method = RequestMethod.GET)
    @ResponseBody
    public Object queryProductByType(@RequestParam(value = "currPage", required=false) Integer currPage,@RequestParam("typeId") Integer typeId,HttpSession session) {


        if (ObjectUtils.isEmpty(currPage)){
            currPage=1;
        }
        BaseInfo baseInfo=(BaseInfo)session.getAttribute("baseInfo");

        PageResult pageResult=productService.queryProductInfoBysellIdAndtypeId(currPage,typeId,baseInfo.getSellId());
        return pageResult;

    }



    /**
     * 进入指定菜品类型的页面
     */
    @RequestMapping(value = "/TypeIndex", method = RequestMethod.GET)
    public String TypeIndex(@RequestParam("typeId") String typeId,@RequestParam("Name") String name, Model model) {

        model.addAttribute("typeId",typeId);
        model.addAttribute("name",name);

        return "/TypeIndex";
    }

    /**
     * 查询指定商家的菜品类型
     */
    @RequestMapping(value = "/queryProductType",method = RequestMethod.GET)
    @ResponseBody
    public Object queryProductType(HttpSession session) {
        BaseInfo baseInfo=(BaseInfo)session.getAttribute("baseInfo");

        return  productService.queryProductType(baseInfo.getSellId());

    }

}
