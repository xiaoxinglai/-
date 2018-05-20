package com.nchu.mapper;

import com.nchu.pojo.DO.OrderMaster;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMasterMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderMaster record);

    OrderMaster selectByPrimaryKey(String orderId);

    List<OrderMaster> selectAll();

    int updateByPrimaryKey(OrderMaster record);
}