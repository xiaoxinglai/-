package com.nchu.product.service.imp;

import com.nchu.pojo.DO.ProductCategory;
import com.nchu.pojo.DO.ProductInfo;
import com.nchu.common.Result.PageResult;
import com.nchu.common.Result.Result;
import com.nchu.mapper.ex.ProductCategoryMapperEx;
import com.nchu.mapper.ex.ProductInfoMapperEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nchu.product.service.IProductService;

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
     * 查询指定商家下的所有菜品类型带分页
     * @param SellId
     * @return
     */
    @Override
    public PageResult<ProductCategory> queryProductTypePage(String SellId) {


       List<ProductCategory> productCategories= productCategoryMapperEx.selectBySellId(SellId);
        PageResult pageResult=new PageResult();
        pageResult.setSuccess(Boolean.TRUE);
        pageResult.setCode(0);
        pageResult.setData(productCategories);
       Integer count= productCategoryMapperEx.CountBySellId(SellId);
        pageResult.setCount(count);

        return pageResult;
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

    /**
     * 根据条件查询商家的所有菜品
     * @param currentPage
     * @param name
     * @param TypeId
     * @param sellId
     * @return
     */
    @Override
    public PageResult<ProductInfo> queryProductByCondition(Integer currentPage, String name, Integer TypeId, String sellId,Integer Status) {

        List<ProductInfo> productInfos = productInfoMapperEx.selectByPageAndsellIdAndTypeIdAndName((currentPage - 1) * PageResult.pageSize, PageResult.pageSize,name,sellId,TypeId,Status);
        Integer totalSize= productInfoMapperEx.selectCountByPageAndsellIdAndTypeIdAndName(sellId,name,TypeId,Status);


        return PageResult.Create(productInfos, currentPage, totalSize/PageResult.pageSize+1);

    }

    @Override
    public Result UpOrDownProduct(String ProductId, Integer status) {

        ProductInfo productInfo=productInfoMapperEx.selectByPrimaryKey(ProductId);
        productInfo.setProductStatus(status.byteValue());
        productInfoMapperEx.updateByPrimaryKey(productInfo);
        return Result.Create();
    }
}
