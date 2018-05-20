package com.nchu.product.service;

import com.nchu.pojo.DO.ProductCategory;
import com.nchu.pojo.DO.ProductInfo;
import com.nchu.common.Result.PageResult;
import com.nchu.common.Result.Result;

import java.util.List;

public interface IProductService {

    //查询所有菜品
    PageResult<ProductInfo> queryProductInfoBysellId(Integer currentPage, String sellId);

    //查询指定商家的菜品类型
    List<ProductCategory> queryProductType(String SellId);
    //查询指定商家的菜品类型带分页
    PageResult<ProductCategory> queryProductTypePage(String SellId);

    //按类型查询所有菜品
    PageResult<ProductInfo> queryProductInfoBysellIdAndtypeId(Integer currentPage, Integer TypeId, String sellId);

    PageResult<ProductInfo> queryProductByCondition(Integer currentPage, String name, Integer TypeId, String sellId, Integer Status);

    //商品上下架
    Result UpOrDownProduct(String ProductId, Integer status);
}
