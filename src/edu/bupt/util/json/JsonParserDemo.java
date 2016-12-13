package edu.bupt.util.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by shixu on 2016/11/30.
 */
public class JsonParserDemo {
    public static void main(String[] args) throws JSONException {

    }


    /**
     * 测试生成json对象
     * @throws JSONException
     */
    @Test
    public void testBuildJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",1);
        jsonObject.put("name","shixu");
        jsonObject.put("age",23);
        jsonObject.put("id",2);  //JsonObject底层是维护一个map,所以不能有重复的键,这里会把之前的值覆盖掉
        System.out.println(jsonObject);
        System.out.println("id:"+jsonObject.get("id"));
        System.out.println("name:"+jsonObject.get("name"));
        System.out.println("age:"+jsonObject.get("age"));




        HashMap map = new HashMap<>();
        map.put("id",1);
        map.put("name","shixu");
        JSONObject jsonObjectMap= new JSONObject(map);  //使用map初始化
        System.out.println(jsonObjectMap);
        System.out.println("id:"+jsonObjectMap.get("id"));
        System.out.println("name:"+jsonObjectMap.get("name"));

    }


    /**
     * 测试生成JSONArray ,用于存放jsonObject
     * @throws JSONException
     */
    @Test
    public void testBuildJsonArray() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        HashMap map = new HashMap<>();
        map.put("id",1);
        map.put("name","shixu");
        JSONObject jsonObjectMap= new JSONObject(map);  //使用map初始化
        jsonArray.put(jsonObjectMap);
        jsonArray.put(jsonObjectMap);
        System.out.println(jsonArray);
        System.out.println(jsonArray.getJSONObject(0));
        System.out.println(jsonArray.getJSONObject(1));
    }

    /**
     * 将一个json字符串解析为json对象
     * @throws JSONException
     *
     */
    @Test
    public void jsonStrToJsonObjectParser() throws JSONException {
        String jsonStr = "[{\"id\":1,\"name\":\"shixu\"},{\"id\":2,\"name\":\"suntime\"}]";
        JSONTokener jsonTokener = new JSONTokener(jsonStr);
        JSONArray jsonArray = new JSONArray(jsonTokener);
        for (int i = 0; i <jsonArray.length() ; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject);
            System.out.print("id:"+jsonObject.get("id"));
            System.out.println("\tname:"+jsonObject.get("name"));
        }
    }
}
