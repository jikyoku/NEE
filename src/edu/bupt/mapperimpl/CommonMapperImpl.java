package edu.bupt.mapperimpl;

import edu.bupt.mapper.CommonMapper;
import edu.bupt.util.SessionFactoryUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import java.util.List;
import java.util.Map;

/**
 * Created by shi xu on 2016/12/5.
 *
 */
public class CommonMapperImpl implements CommonMapper {
    @Override
    public Map<String, Map<String, Integer>> getDfMap() {
        SqlSession sqlSession = SessionFactoryUtil.getSessionFactory().openSession();
        CommonMapper cm = sqlSession.getMapper(CommonMapper.class);
        Map<String, Map<String, Integer>> dfMap = cm.getDfMap();
        sqlSession.close();
        return dfMap;
    }

    @Override
    public Map<String, Map<String, Object>> getDFMap() {
        SqlSession sqlSession = SessionFactoryUtil.openSeeion();
        CommonMapper commonMapper = new CommonMapperImpl();
        Map<String, Map<String, Object>> map = commonMapper.getDFMap();
        sqlSession.close();
        return map;
    }

    @Override
    public void insertWord(List<String> wordSet) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        CommonMapper cm = session.getMapper(CommonMapper.class);
        cm.insertWord(wordSet);
        session.commit();
        session.close();
    }

    @Override
    public String getCoreSentByTitle(String title) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        CommonMapper cm = session.getMapper(CommonMapper.class);
        String coreSent = cm.getCoreSentByTitle(title);
        session.close();
        return coreSent;
    }

    @Override
    public Integer getLogEndId() {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        CommonMapper cm = session.getMapper(CommonMapper.class);
        Integer endId = cm.getLogEndId();
        session.close();
        return endId;
    }

    @Override
    public void insertNewLog(@Param("startId") Integer startId, @Param("endId") Integer endId) {
        SqlSession session = SessionFactoryUtil.openSeeion();
        CommonMapper cm = session.getMapper(CommonMapper.class);
        cm.insertNewLog(startId, endId);
        session.commit();
        session.close();
    }

    @Override
    public void insertDF(Map<String,Map<String,Integer>>  params) {
        SqlSession session = SessionFactoryUtil.openSeeion();
        CommonMapper cm = session.getMapper(CommonMapper.class);
        cm.insertDF(params);
        session.commit();
        session.close();
    }

    @Override
    public void updateDfOne(@Param("word") String word, @Param("df") Integer df) {
        SqlSession session = SessionFactoryUtil.openSeeion();
        CommonMapper cm = session.getMapper(CommonMapper.class);
        cm.updateDfOne(word,df);
        session.commit();
        session.close();
    }

    @Override
    public void insertDFOne(@Param("word") String word, @Param("df") Integer df) {
        SqlSession session = SessionFactoryUtil.openSeeion();
        CommonMapper cm = session.getMapper(CommonMapper.class);
        cm.insertDFOne(word,df);
        session.commit();
        session.close();
    }

    @Override
    public Integer getSize() {
        SqlSession session = SessionFactoryUtil.openSeeion();
        CommonMapper cm = session.getMapper(CommonMapper.class);
        Integer size = cm.getSize();
        session.close();
        return size;
    }

    @Override
    public void updateSize(Integer size) {
        SqlSession session = SessionFactoryUtil.openSeeion();
        CommonMapper cm = session.getMapper(CommonMapper.class);
        cm.updateSize(size);
        session.commit();
        session.close();
    }
}
