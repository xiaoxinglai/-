package com.nchu.mapper;

import com.nchu.pojo.DO.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    int deleteByPrimaryKey(String detailId);

    int insert(OrderDetail record);

    OrderDetail selectByPrimaryKey(String detailId);

    List<OrderDetail> selectAll();

    int updateByPrimaryKey(OrderDetail record);
}