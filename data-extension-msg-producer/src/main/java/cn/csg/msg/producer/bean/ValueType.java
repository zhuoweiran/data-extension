package cn.csg.msg.producer.bean;

/**
 * 枚举{@code ValueType}定义了多种模板参数的类型
 *
 * <p>String,Int,Object,Table_Device,Table_TmpDevice,Array分别为
 * 字符串类型，整形，Json类型，已知资产类型，未知资产类型和Json Array类型</p>
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 */
public enum ValueType {
    //去掉对date的支持，freemarker自带的日期及函数已足够使用
    String,Int//,Date
    ,Object,Table_Device,Table_TmpDevice,Array,Random_int,GUID
}
