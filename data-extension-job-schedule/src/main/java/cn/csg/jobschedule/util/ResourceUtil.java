package cn.csg.jobschedule.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/***
 * @Author: xushiyong
 * @Description
 * @Date: Created in 10:36 2018/11/6
 * @Modify By:
 **/
public class ResourceUtil {

    private static final Logger logger = LoggerFactory.getLogger(ResourceUtil.class);

    /**
     *  加载配置文件
     * @param filename
     * @return props
     */
    public static Properties load(String filename){
        Properties props = new Properties();
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")) {
            filename = "src/main/resources/"+filename;
            String pathName = new File("").getAbsolutePath();
            InputStream is = null ;
            try{
                is = new FileInputStream(new File(pathName+File.separator+filename));
                props.load(is);
            }catch (Exception e){
                throw new RuntimeException("加载配置文件时失败", e);
            } finally {
                close(is) ;
            }
        }else{
            //filename = "/apps/bigdata/resources/"+filename;
            //InputStream is = null ;
            FileInputStream fileInputStream = null ;
            try{
                //is = new FileInputStream(new File(filename));
               fileInputStream = new FileInputStream(filename);
                props.load(fileInputStream);
            }catch (Exception e){
                throw new RuntimeException("加载配置文件时失败", e);
            } finally {
                close(fileInputStream) ;
            }
        }

        return props ;

    }


    /**
     * 批量关闭 java.io.Closeable
     * @param objs
     */
    public static void close(Closeable... objs) {
        for (Closeable obj : objs) {
            if (obj != null) {
                try {
                    obj.close();
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}
