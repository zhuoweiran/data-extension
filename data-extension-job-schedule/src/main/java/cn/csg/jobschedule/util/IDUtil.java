package cn.csg.jobschedule.util;

import java.util.UUID;

/***
 * @Author: xushiyong
 * @Description 生成UUID
 * @Date: Created in 18:28 2018/11/6
 * @Modify By:
 **/
public class IDUtil {
    public static String  getUUID(){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid ;
    }
}
