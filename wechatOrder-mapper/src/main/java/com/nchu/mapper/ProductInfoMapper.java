package com.nchu.mapper;

import com.nchu.pojo.DO.ProductInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductInfoMapper {
    int deleteByPrimaryKey(String productId);

    int insert(ProductInfo record);

    ProductInfo selectByPrimaryKey(String productId);

    List<ProductInfo> selectAll();

    int updateByPrimaryKey(ProductInfo record);
}