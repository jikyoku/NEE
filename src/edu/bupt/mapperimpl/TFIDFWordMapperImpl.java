package edu.bupt.mapperimpl;

import edu.bupt.mapper.TFIDFWordMapper;
import edu.bupt.model.TFIDFWord;
import edu.bupt.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by shixu on 2016/11/15.
 */
public class TFIDFWordMapperImpl implements TFIDFWordMapper {
    @Override
    public void insertTF(List<TFIDFWord> tfidfWords) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        TFIDFWordMapper tfidfWordMapper = session.getMapper(TFIDFWordMapper.class);
        tfidfWordMapper.insertTF(tfidfWords);
        session.commit();
        session.close();
    }

    @Override
    public List<String> getWords() {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        TFIDFWordMapper tfidfWordMapper = session.getMapper(TFIDFWordMapper.class);
        List<String> words = tfidfWordMapper.getWords();
        session.close();
        return words;
    }

    @Override
    public void insertDF(List<TFIDFWord> tfidfWords) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        TFIDFWordMapper tfidfWordMapper = session.getMapper(TFIDFWordMapper.class);
        tfidfWordMapper.insertDF(tfidfWords);
        session.commit();
        session.close();

    }

    @Override
    public void insertTFIDF(List<TFIDFWord> tfidfWords) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        TFIDFWordMapper tfidfWordMapper = session.getMapper(TFIDFWordMapper.class);
        tfidfWordMapper.insertTFIDF(tfidfWords);
        session.commit();
        session.close();
    }

    @Override
    public List<String> getTFIDFWords() {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        TFIDFWordMapper tfidfWordMapper = session.getMapper(TFIDFWordMapper.class);
        List<String> wordList = tfidfWordMapper.getTFIDFWords();
        session.close();
        return wordList;
    }


}
