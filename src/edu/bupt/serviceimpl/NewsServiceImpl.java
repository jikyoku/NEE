package edu.bupt.serviceimpl;

import edu.bupt.mapper.NewsMapper;
import edu.bupt.mapperimpl.NewsMapperImpl;
import edu.bupt.model.News;
import edu.bupt.service.NewsService;
import org.junit.*;

import java.util.List;
import java.util.Map;

/**
 * Created by shi xu on 2016-07-30.
 */
public class NewsServiceImpl implements NewsService {

    private NewsMapper newsMapper = new NewsMapperImpl();

    @Override
    public int addNews(News news) {
        return newsMapper.addNews(news);
    }

    @Override
    public List<Integer> getIdList() {
        return newsMapper.getIdList();
    }

    @Override
    public String getNewsById(int id) {
        return newsMapper.getNewsById(id);
    }

    @Override
    public List<String> getNews(int startId, int n) {
        return newsMapper.getNews(startId, n);
    }

    @Override
    public Map<Integer, News> getNewsMap(Integer startId, Integer endId, Integer length) {
        return newsMapper.getNewsMap(startId, endId, length);
    }

    @Override
    public int getNewsCount() {
        return newsMapper.getNewsCount();
    }

    @Override
    public List<String> getNewsList() {
        return newsMapper.getNewsList();
    }

    @Override
    public List<String> getNewsList(int startId, int length) {
        return newsMapper.getNewsContentList(startId, length);
    }


    @org.junit.Test
    public void test() {
        List<String> newsList = getNews(20, 10);
        System.out.println(newsList);
    }
}
