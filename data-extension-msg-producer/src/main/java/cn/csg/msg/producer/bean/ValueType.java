package cn.csg.msg.producer.bean;

public enum ValueType {
    //去掉对date的支持，freemarker自带的日期及函数已足够使用
    String,Int//,Date
    ,Object,Table_Device,Table_TmpDevice,Array
}
