package com.nchu.common.Enum;

/**
 * Created by user12 on 2018/2/11.
 */
public enum ProductEnum {
    UP(1, "上架"),
    DOWN(0, "下架");
    private Integer code;
    private String msg;

    ProductEnum(Integer code, String msg) {

        this.code = code;
        this.msg = msg;
    }



    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
