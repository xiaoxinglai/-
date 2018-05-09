package com.nchu.wechatOrder.service.imp;

import com.nchu.wechatOrder.domain.DO.SellerInfo;
import com.nchu.wechatOrder.mapper.SellerInfoMapper;
import com.nchu.wechatOrder.service.ISellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellServiceImpl implements ISellService {

    @Autowired
    private SellerInfoMapper sellerInfoMapper;


    @Override
    public SellerInfo querySellerInfo(String sellId) {
        SellerInfo sellerInfo= sellerInfoMapper.selectByPrimaryKey(sellId);

        return sellerInfo;
    }
}
