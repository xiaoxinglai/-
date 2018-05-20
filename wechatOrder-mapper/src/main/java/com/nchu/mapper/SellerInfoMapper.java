package com.nchu.mapper;

import com.nchu.pojo.DO.SellerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellerInfoMapper {
    int deleteByPrimaryKey(String sellId);

    int insert(SellerInfo record);

    SellerInfo selectByPrimaryKey(String sellId);

    List<SellerInfo> selectAll();

    int updateByPrimaryKey(SellerInfo record);
}