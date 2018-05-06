package com.nchu.wechatOrder.mapper;

import com.nchu.wechatOrder.domain.DO.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductCategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(ProductCategory record);

    ProductCategory selectByPrimaryKey(Integer categoryId);

    List<ProductCategory> selectAll();

    int updateByPrimaryKey(ProductCategory record);
}