package com.nchu.wechatOrder.utils;

import java.util.Random;


public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
    /**
     * 生成唯一的菜品主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String genProductUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900) + 100;
        String id=String.valueOf(System.currentTimeMillis());
        String identity=id.substring(1,9)+String.valueOf(number);
        return identity.trim();
    }

    /**
     * 生成唯一的菜品类型主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized Integer genProducTypetUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900) + 100;
        Long re=System.currentTimeMillis()+number;

        return  Long.valueOf(re).intValue();
    }
}
