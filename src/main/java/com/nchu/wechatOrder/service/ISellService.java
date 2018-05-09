package com.nchu.wechatOrder.service;

import com.nchu.wechatOrder.domain.DO.SellerInfo;

public interface ISellService {

    SellerInfo querySellerInfo(String sellId);

}
