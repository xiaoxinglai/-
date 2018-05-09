package com.nchu.wechatOrder.service;

import com.nchu.wechatOrder.domain.DO.ProductCategory;
import com.nchu.wechatOrder.domain.DO.ProductInfo;
import com.nchu.wechatOrder.domain.VO.PageResult;

import java.util.List;

public interface IProductService {

    //查询所有菜品
    PageResult<ProductInfo> queryProductInfoBysellId(Integer currentPage,String sellId);
    //查询指定商家的菜品类型
    List<ProductCategory> queryProductType(String SellId);

    //按类型查询所有菜品
    PageResult<ProductInfo> queryProductInfoBysellIdAndtypeId(Integer currentPage,Integer TypeId,String sellId);


}
