package com.nchu.wechatOrder.service.imp;

import com.nchu.wechatOrder.domain.DO.ProductCategory;
import com.nchu.wechatOrder.domain.DO.ProductInfo;
import com.nchu.wechatOrder.domain.VO.PageResult;
import com.nchu.wechatOrder.mapper.ex.ProductCategoryMapperEx;
import com.nchu.wechatOrder.mapper.ex.ProductInfoMapperEx;
import com.nchu.wechatOrder.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {


    @Autowired
    private ProductInfoMapperEx productInfoMapperEx;
    @Autowired
    private ProductCategoryMapperEx productCategoryMapperEx;

    /**
     * 查询所有菜品
     * @param currentPage
     * @param sellId
     * @return
     */
    @Override
    public PageResult<ProductInfo> queryProductInfoBysellId(Integer currentPage, String sellId) {
        List<ProductInfo> productInfos = productInfoMapperEx.selectByPageAndsellId((currentPage - 1) * PageResult.pageSize, PageResult.pageSize,sellId);
        Integer totalSize= productInfoMapperEx.selectCountByPageAndsellId(sellId);

        return PageResult.Create(productInfos, currentPage, totalSize/PageResult.pageSize+1);

    }

    /**
     * 查询指定商家下的所有菜品类型
     * @param SellId
     * @return
     */
    @Override
    public List<ProductCategory> queryProductType(String SellId) {

        return  productCategoryMapperEx.selectBySellId(SellId);

    }

    /**
     *查询所有菜品按照商家和菜品类型
     * @param currentPage
     * @param TypeId
     * @param sellId
     * @return
     */
    @Override
    public PageResult<ProductInfo> queryProductInfoBysellIdAndtypeId(Integer currentPage, Integer TypeId, String sellId) {
        List<ProductInfo> productInfos = productInfoMapperEx.selectByPageAndsellIdAndTypeId((currentPage - 1) * PageResult.pageSize, PageResult.pageSize,sellId,TypeId);
        Integer totalSize= productInfoMapperEx.selectCountByPageAndsellIdAndTypeId(sellId,TypeId);

        return PageResult.Create(productInfos, currentPage, totalSize/PageResult.pageSize+1);

    }
}
