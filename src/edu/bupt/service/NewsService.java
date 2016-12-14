package edu.bupt.service;

import edu.bupt.model.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by shixu on 2016-07-30.
 */
public interface NewsService {
    int addNews(News news);

    List<Integer> getIdList();

    String getNewsById(int id);

    List<String> getNews(int startId, int n);

    Map<Integer, News> getNewsMap(Integer startId, Integer endId, Integer length);

    int getNewsCount();


    List<String> getNewsList(Integer startId, Integer length);
}
