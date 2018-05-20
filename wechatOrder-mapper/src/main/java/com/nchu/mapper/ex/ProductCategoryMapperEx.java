package com.nchu.mapper.ex;

import com.nchu.pojo.DO.ProductCategory;
import com.nchu.mapper.ProductCategoryMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductCategoryMapperEx  extends ProductCategoryMapper {

    int insertAll(ProductCategory record);
    List<ProductCategory> selectBySellId(String SellId);
    Integer CountBySellId(String SellId);

}