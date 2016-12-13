package edu.bupt.mapperimpl;

import edu.bupt.mapper.CommonMapper;
import edu.bupt.mapper.NewsMapper;

import java.util.*;

/**
 * Created by shixu on 2016-08-02.
 */
public class Test {
    public static void main(String[] args) {
        NewsMapper  newsMapper = new NewsMapperImpl();
        System.out.println(newsMapper.getNewsMap(1,10,null).size());
    }
}
