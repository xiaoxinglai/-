package com.nchu.wechatOrder.service;

import com.nchu.wechatOrder.domain.DO.SellerInfo;
import com.nchu.wechatOrder.domain.Result.BizResult;

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

}
