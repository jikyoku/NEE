package edu.bupt.mapperimpl;

import edu.bupt.mapper.CommonMapper;
import java.util.*;
/**
 * Created by shixu on 2016-08-02.
 */
public class Test {
    public static void main(String[] args) {
        CommonMapper cm = new CommonMapperImpl();
        Map<String,Integer> dfMap = new HashMap<>();
        dfMap.put("me",1);
        dfMap.put("we",2);
        dfMap.put("werd",2);
        Map<String,Map<String,Integer>> params = new HashMap<>();
        params.put("keys",dfMap);
        cm.insertDF(params);

//        NewsMapper  newsMapper = new NewsMapperImpl();
//        System.out.println(newsMapper.getNewsList(5001,null).size());
    }
}
