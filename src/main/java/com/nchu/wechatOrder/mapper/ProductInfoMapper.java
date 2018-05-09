package com.nchu.wechatOrder.mapper;

import com.nchu.wechatOrder.domain.DO.ProductInfo;
import java.util.List;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(String productId);

    int insert(ProductInfo record);

    ProductInfo selectByPrimaryKey(String productId);

    List<ProductInfo> selectAll();

    int updateByPrimaryKey(ProductInfo record);
}