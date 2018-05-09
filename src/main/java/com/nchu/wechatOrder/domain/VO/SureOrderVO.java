package com.nchu.wechatOrder.domain.VO;

import java.util.Map;

public class SureOrderVO {
    private String OrderId;
    private Map<String,Integer> listMap;

    public SureOrderVO() {
    }

    public SureOrderVO(String orderId, Map<String, Integer> listMap) {
        OrderId = orderId;
        this.listMap = listMap;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public Map<String, Integer> getListMap() {
        return listMap;
    }

    public void setListMap(Map<String, Integer> listMap) {
        this.listMap = listMap;
    }

    @Override
    public String toString() {
        return "SureOrderVO{" +
                "OrderId='" + OrderId + '\'' +
                ", OrderMap=" + listMap +
                '}';
    }
}
