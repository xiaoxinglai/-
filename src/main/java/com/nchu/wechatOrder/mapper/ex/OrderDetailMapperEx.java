package com.nchu.wechatOrder.mapper.ex;

import com.nchu.wechatOrder.domain.DO.OrderDetail;
import com.nchu.wechatOrder.mapper.OrderDetailMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapperEx extends OrderDetailMapper {

    List<OrderDetail> selectByOrderIds(List<String> OrderIds);
    List<OrderDetail> selectByOrderId(String OrderIds);

    int deleteByOrderId(String OrderId);

    List<OrderDetail> selectByDetailIds(List<String> DetailIds);



}