package com.nchu.mapper.ex;

import com.nchu.pojo.DO.ProductInfo;
import com.nchu.mapper.ProductInfoMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductInfoMapperEx  extends ProductInfoMapper {

    int insertAll(ProductInfo record);
    List<ProductInfo> selectByProductIds(List<String> productIds);

    List<ProductInfo> selectByPageAndsellId(@Param("currentPage") Integer currentPage, @Param("pageSize") Integer pageSize, @Param("sellId") String sellId);
    int selectCountByPageAndsellId(String sellId);


    List<ProductInfo> selectByPageAndsellIdAndTypeId(@Param("currentPage") Integer currentPage, @Param("pageSize") Integer pageSize, @Param("sellId") String sellId, @Param("TypeId") Integer TypeId);
    int selectCountByPageAndsellIdAndTypeId(@Param("sellId") String sellId, @Param("TypeId") Integer TypeId);



    List<ProductInfo> selectByPageAndsellIdAndTypeIdAndName(@Param("currentPage") Integer currentPage, @Param("pageSize") Integer pageSize, @Param("name") String name, @Param("sellId") String sellId, @Param("TypeId") Integer TypeId, @Param("Status") Integer Status);
    int selectCountByPageAndsellIdAndTypeIdAndName(@Param("sellId") String sellId, @Param("name") String name, @Param("TypeId") Integer TypeId, @Param("Status") Integer Status);

}