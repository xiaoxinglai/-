package com.nchu.wechatOrder.mapper.ex;

import com.nchu.wechatOrder.domain.DO.SellerInfo;
import com.nchu.wechatOrder.mapper.SellerInfoMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SellerInfoMapperEx extends SellerInfoMapper {

    //根据账号查询商家信息
    SellerInfo selectByNo(String no);


}