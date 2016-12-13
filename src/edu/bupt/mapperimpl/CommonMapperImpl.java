package edu.bupt.mapperimpl;

import edu.bupt.mapper.CommonMapper;
import edu.bupt.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionManager;

import java.util.List;
import java.util.Map;

/**
 * Created by shixu on 2016/12/5.
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
}
