package edu.bupt.mapperimpl;

import edu.bupt.mapper.TriggerWordMapper;
import edu.bupt.model.TriggerWord;
import edu.bupt.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * Created by shixu on 2016-07-30.
 */
public class TriggerWordMapperImpl implements TriggerWordMapper {


    @Override
    public List<TriggerWord> getListOfTriggerWord() {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        TriggerWordMapper triggerWordMapper = session.getMapper(TriggerWordMapper.class);
        List<TriggerWord> triggerWords = triggerWordMapper.getListOfTriggerWord();
        session.close();
        return triggerWords;
    }


    @Override
    public Map<String, TriggerWord> getMapOfTriggerWord() {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        TriggerWordMapper triggerWordMapper = session.getMapper(TriggerWordMapper.class);
        Map<String,TriggerWord> triggerWords = triggerWordMapper.getMapOfTriggerWord();
        session.close();
        return triggerWords;
    }

    @Override
    public int addTrgWd(TriggerWord triggerWord) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        TriggerWordMapper triggerWordMapper = session.getMapper(TriggerWordMapper.class);
        int r =  triggerWordMapper.addTrgWd(triggerWord);
        session.commit();
        session.close();
        return r;
    }

    /**
     * 使用一对一查询关联 resultMap
     * */
    @Override
    public List<TriggerWord> getTwAndSubType() {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        TriggerWordMapper triggerWordMapper = session.getMapper(TriggerWordMapper.class);
        List<TriggerWord> tws = triggerWordMapper.getTwAndSubType();
        session.close();
        return tws;
    }

    @Override
    public List<TriggerWord> getTWByName(String value) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        TriggerWordMapper triggerWordMapper = session.getMapper(TriggerWordMapper.class);
        List<TriggerWord> tws = triggerWordMapper.getTWByName(value);
        session.close();
        return tws;
    }

    public static void main(String[] args) {
        TriggerWordMapperImpl triggerWordDAO = new TriggerWordMapperImpl();
        triggerWordDAO.getTWByName("在线");
    }
}
