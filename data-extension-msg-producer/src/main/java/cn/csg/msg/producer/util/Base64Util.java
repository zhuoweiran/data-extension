package cn.csg.msg.producer.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * 类{@code Base64Util}Base64加密
 *
 * @author Liqin
 * @since 1.2
 * @version 1.2
 */
public class Base64Util {
    /**
     * file encode
     * @param file 文件
     * @return String
     */
    public static String file2String(File file){
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String temp;
            while((temp = buffer.readLine()) !=null ){
                sb.append(temp);

            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加密
     * @param str 输入
     * @return String
     */
    public static String getBase64(String str){
        byte[]  b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if( b != null){
            s = new BASE64Encoder().encode(b);
        }
        return s;

    }

    /**
     * 解密
     * @param str 输入
     * @return String
     */
    public static String getFromBase64(String str){
        byte[] b = null;
        String result = null;
        if(str != null){
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(str);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * encode 文件
     * @param file 文件
     * @return String
     * @throws IOException
     */
    public static String encode(File file) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        StringBuilder sb = new StringBuilder();
        InputStream input = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];
        for (int len = input.read(temp); len != -1; len = input
                .read(temp)) {
            out.write(temp, 0, len);
            sb.append(encoder.encode(out.toByteArray()));
            // out(pictureBuffer.toString());
            out.reset();
        }
        return sb.toString();
    }
}
