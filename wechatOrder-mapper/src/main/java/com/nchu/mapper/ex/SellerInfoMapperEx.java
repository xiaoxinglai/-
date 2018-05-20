package com.nchu.mapper.ex;

import com.nchu.mapper.SellerInfoMapper;
import com.nchu.pojo.DO.SellerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellerInfoMapperEx extends SellerInfoMapper {

    //根据账号查询商家信息
    SellerInfo selectByNo(String no);
    List<SellerInfo> querySellBypage(Byte Status);
    Integer CountBySellBypage(Byte Status);


}