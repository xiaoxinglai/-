package com.nchu.mapper.ex;

import com.nchu.pojo.DO.OrderDetail;
import com.nchu.mapper.OrderDetailMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapperEx extends OrderDetailMapper {

    List<OrderDetail> selectByOrderIds(List<String> OrderIds);
    List<OrderDetail> selectByOrderId(String OrderIds);

    int deleteByOrderId(String OrderId);

    List<OrderDetail> selectByDetailIds(List<String> DetailIds);



}