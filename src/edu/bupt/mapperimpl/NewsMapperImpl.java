package edu.bupt.mapperimpl;

import edu.bupt.mapper.NewsMapper;
import edu.bupt.model.News;
import edu.bupt.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import java.util.List;
import java.util.Map;

/**
 * Created by shixu on 2016-07-30.
 */
public class NewsMapperImpl implements NewsMapper {
	
	@Override
	public void addNewsBatch(List<News> newsList){
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        NewsMapper newsMapper = session.getMapper(NewsMapper.class);
        newsMapper.addNewsBatch(newsList);
        session.commit();
        session.close();
	}


    @Override
    public int addNews(News news) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        NewsMapper newsMapper = session.getMapper(NewsMapper.class);
        int r = newsMapper.addNews(news);
        session.commit();
        session.close();
        return r;
    }

    @Override
    public List<Integer> getIdList() {
        SqlSession sqlsession = SessionFactoryUtil.getSessionFactory().openSession();
        NewsMapper newsMapper = sqlsession.getMapper(NewsMapper.class);
        List<Integer> idList = newsMapper.getIdList();
        sqlsession.close();
        return idList;
    }



    @Override
    public String getNewsById(int id) {

        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        NewsMapper newsMapper = session.getMapper(NewsMapper.class);
        String newsCont = newsMapper.getNewsById(id);
        session.close();
        return newsCont;
    }

    @Override
    public List<String> getNews(int startId, int n) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        NewsMapper newsMapper = session.getMapper(NewsMapper.class);
        List<String> newsList = newsMapper.getNews(startId, n);
        session.close();
        return newsList;
    }

    @Override
    public Map<Integer, News> getNewsMap(Integer startId, Integer endId, Integer n) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        NewsMapper newsMapper = session.getMapper(NewsMapper.class);
        Map<Integer, News> newsMap = newsMapper.getNewsMap(startId, endId, n);
        session.close();
        return newsMap;
    }


    @Override
    public int getNewsCount() {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        NewsMapper newsMapper = session.getMapper(NewsMapper.class);
        int count = newsMapper.getNewsCount();
        session.close();
        return count;
    }

    @Override
    public List<String> getNewsList(Integer startId, Integer length) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        NewsMapper newsMapper = session.getMapper(NewsMapper.class);
        List<String> newsList = newsMapper.getNewsList(startId, length);
        session.close();
        return newsList;
    }


}
