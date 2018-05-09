package com.nchu.wechatOrder.domain.VO;

import java.io.Serializable;

public class BaseInfo implements Serializable {


    private static final long serialVersionUID = 4327471841751252435L;
    private  String buyerAddress;
    private String buyerOpenId;
   private String sellId;

    public BaseInfo() {
    }

    public BaseInfo(String buyerAddress, String buyerOpenId, String sellId) {
        this.buyerAddress = buyerAddress;
        this.buyerOpenId = buyerOpenId;
        this.sellId = sellId;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getBuyerOpenId() {
        return buyerOpenId;
    }

    public void setBuyerOpenId(String buyerOpenId) {
        this.buyerOpenId = buyerOpenId;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
    }
}
