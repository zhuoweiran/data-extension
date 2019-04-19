package cn.csg.util;

import cn.csg.util.exception.JsonParseException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class JsonParser {
    public static JSONObject parse(String jsonStr) throws JsonParseException{
        JSONObject jsonObject = null;
        try{
            jsonObject = JSON.parseObject(jsonStr);
        }catch (Exception e){
            jsonObject = new JSONObject(new HashMap<String, Object>() {{
                put("msg",jsonStr);
            }});
            //throw new JsonParseException(e.getMessage(),e.getCause());
        }
        return jsonObject;
    }
}
