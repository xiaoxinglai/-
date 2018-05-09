package com.nchu.wechatOrder.service.imp;

import com.nchu.wechatOrder.domain.DO.ProductInfo;
import com.nchu.wechatOrder.domain.VO.PageResult;
import com.nchu.wechatOrder.mapper.ex.ProductInfoMapperEx;
import com.nchu.wechatOrder.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {


    @Autowired
    private ProductInfoMapperEx productInfoMapperEx;

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
}
