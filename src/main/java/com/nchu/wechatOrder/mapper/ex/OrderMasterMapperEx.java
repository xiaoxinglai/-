package com.nchu.wechatOrder.mapper.ex;

import com.nchu.wechatOrder.domain.DO.OrderMaster;
import com.nchu.wechatOrder.mapper.OrderMasterMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMasterMapperEx extends OrderMasterMapper {


    //查询根据用户Id和订单状态 分页查询订单
    List<OrderMaster> selectByOpenId(@Param("currPage") Integer currPage,@Param("PageSize") Integer PageSize,@Param("openId") String OpenId, @Param("payStatus") Integer payStatus);
    Integer selectCountByPageAndOpenId(@Param("openId") String OpenId, @Param("payStatus") Integer payStatus);

}