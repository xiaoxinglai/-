package com.nchu.product.service;

import com.nchu.pojo.DO.SellerInfo;
import com.nchu.common.Result.PageResult;
import com.nchu.common.Result.BizResult;

public interface ISellService {

    /**
     * 根据sellId查询商家信息
     * @param sellId
     * @return
     */
    SellerInfo querySellerInfo(String sellId);
    /**
     * 商家登陆的方法
     */
    BizResult<SellerInfo> doLogin(SellerInfo user);

    /**
     * 获取所有的商家信息 带分页
     */
    PageResult<SellerInfo> querySellBypage(Byte status);

}
