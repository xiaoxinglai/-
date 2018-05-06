package com.nchu.wechatOrder.mapper;

import com.nchu.wechatOrder.domain.DO.ProductInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductInfoMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(ProductInfo record);

    ProductInfo selectByPrimaryKey(Integer productId);

    List<ProductInfo> selectAll();

    int updateByPrimaryKey(ProductInfo record);
}