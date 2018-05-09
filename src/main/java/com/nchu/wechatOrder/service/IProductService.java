package com.nchu.wechatOrder.service;

import com.nchu.wechatOrder.domain.DO.ProductInfo;
import com.nchu.wechatOrder.domain.VO.PageResult;

public interface IProductService {

    //查询所有菜品
    PageResult<ProductInfo> queryProductInfoBysellId(Integer currentPage,String sellId);


}
