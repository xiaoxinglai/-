package com.nchu.wechatOrder.mapper.ex;

import com.nchu.wechatOrder.domain.DO.ProductInfo;
import com.nchu.wechatOrder.mapper.ProductInfoMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductInfoMapperEx  extends ProductInfoMapper {


    List<ProductInfo> selectByProductIds(List<String> productIds);

    List<ProductInfo> selectByPageAndsellId(@Param("currentPage") Integer currentPage,@Param("pageSize") Integer pageSize,@Param("sellId") String sellId);
    int selectCountByPageAndsellId(String sellId);

}