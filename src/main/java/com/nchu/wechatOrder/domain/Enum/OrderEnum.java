package com.nchu.wechatOrder.domain.Enum;

/**
 * Created by user12 on 2018/2/3.
 */
public enum OrderEnum {
    Pay(1,"已支付,等待商家处理"),
    APPLY_PAYBACK(2,"申请退款"),
    NO_Pay(3,"未支付"),
    SURE_ORDER(4,"商家确认接单"),
    PAYBACK(5,"已退款")
    ;
    private Integer code;
    private String msg;

    OrderEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;

    }


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
