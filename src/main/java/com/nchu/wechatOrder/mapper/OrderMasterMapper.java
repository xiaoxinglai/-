package com.nchu.wechatOrder.mapper;

import com.nchu.wechatOrder.domain.DO.OrderMaster;
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