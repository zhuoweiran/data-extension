package cn.csg.util;


import java.math.BigDecimal;

public class EvevySecond2CronExpression {
    public static String format(BigDecimal second){

        Integer a = second.intValue();
        return String.format("0/%s * * * * ? *",a);
    }
}
