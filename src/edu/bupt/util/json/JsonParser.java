package edu.bupt.util.json;

import edu.bupt.model.Argument;
import edu.bupt.model.Event;
import edu.bupt.model.Word;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shixu on 2016/11/30.
 */
public class JsonParser {
    public static void main(String[] args) throws JSONException {
    	Event event = new Event();
    	event.setA0("a0");
    	event.setA1("a1");

    	
        String json = "[\n" +
                "  [\n" +
                "    [\n" +
                "      {\n" +
                "        \"id\": 0,\n" +
                "        \"cont\": \"中国\",\n" +
                "        \"pos\": \"ns\",\n" +
                "        \"ne\": \"S-Ns\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"cont\": \"国家\",\n" +
                "        \"pos\": \"n\",\n" +
                "        \"ne\": \"O\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"cont\": \"主席\",\n" +
                "        \"pos\": \"n\",\n" +
                "        \"ne\": \"O\"\n" +
                "      }" +
                "    ]\n" +
                "  ]\n" +
                "]";

        JSONArray jsonArray = getJsonArrayByStr(json);
        System.out.println(parseJsonArrayToWords(jsonArray));
    }
    
    
    public static JSONArray buildJSONArray(List<Event> events){
    	JSONArray jsonArray =  new JSONArray();
    	for (int i = 0; i < events.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("LOC-位置",events.get(i).getLOC());
            map.put("A0-主体",events.get(i).getA0());
            map.put("A1-受体", events.get(i).getA1());
            map.put("TMP-时间", events.get(i).getTMP());
            if (events.get(i).getHED() != null){
            	map.put("HEAD-中心词", events.get(i).getHED().getCont());
            }
            //map.put("HEAD-中心词", events.get(i).getHED().getCont());
            map.put("ACTION-核心动词", events.get(i).getAction().getCont());
            JSONObject jsonObjectMap= new JSONObject(map);  //使用map初始化
            jsonArray.put(jsonObjectMap);
    	}
    	return jsonArray;
    }


    public static JSONArray getJsonArrayByStr(String jsonStr) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonStr).getJSONArray(0).getJSONArray(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static List<Word> parseJsonArrayToWords(JSONArray jsonArray) {
        List<Word> wordList = null;
        Word word;
        try {
            wordList = new ArrayList<>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                word = new Word();
                word.setId(jsonObject.getInt("id"));
                if (jsonObject.has("cont")) {
                    word.setCont(jsonObject.getString("cont"));
                }
                if (jsonObject.has("ne")) {
                    word.setNe(jsonObject.getString("ne"));
                }
                if (jsonObject.has("pos")) {
                    word.setPos(jsonObject.getString("pos"));
                }
                if (jsonObject.has("parent")) {
                    word.setParent(jsonObject.getInt("parent"));
                }
                if (jsonObject.has("relate")) {
                    word.setRelate(jsonObject.getString("relate"));
                }
                if (jsonObject.has("arg")) {
                    JSONArray argJsonArray = jsonObject.getJSONArray("arg");
                    JSONObject argJsonObject;
                    Argument argument;
                    if (argJsonArray.length() != 0) {
                        List<Argument> argumentList = new ArrayList<>(argJsonArray.length());
                        for (int j = 0; j < argJsonArray.length(); j++) {
                            argJsonObject = argJsonArray.getJSONObject(j);
                            argument = new Argument();
                            argument.setId(argJsonObject.getInt("id"));
                            argument.setBeg(argJsonObject.getInt("beg"));
                            argument.setEnd(argJsonObject.getInt("end"));
                            argument.setType(argJsonObject.getString("type"));
                            argumentList.add(argument);
                        }
                        word.setArguments(argumentList);
                    }

                }
                wordList.add(word);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wordList;
    }
}
