package com.nchu.wechatOrder.service.imp;

import com.nchu.wechatOrder.domain.DO.SellerInfo;
import com.nchu.wechatOrder.domain.Enum.ParamEnum;
import com.nchu.wechatOrder.domain.Result.BizResult;
import com.nchu.wechatOrder.mapper.ex.SellerInfoMapperEx;
import com.nchu.wechatOrder.service.ISellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellServiceImpl implements ISellService {

    @Autowired
    private SellerInfoMapperEx sellerInfoMapper;


    @Override
    public SellerInfo querySellerInfo(String sellId) {
        SellerInfo sellerInfo= sellerInfoMapper.selectByPrimaryKey(sellId);

        return sellerInfo;
    }

    @Override
    public BizResult<SellerInfo> doLogin(SellerInfo user) {
        SellerInfo result = sellerInfoMapper.selectByNo(user.getUserCount());
        if (result == null) {
            return BizResult.Create(ParamEnum.NO_USER.getCode(), ParamEnum.NO_USER.getMsg());
        }

        if (result.getPassword().equals(user.getPassword())) {

            return BizResult.Create(result);
        } else {
            return BizResult.Create(ParamEnum.PASS_ERR.getCode(), ParamEnum.PASS_ERR.getMsg());
        }
    }
}
