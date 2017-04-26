package edu.bupt.mapperimpl;

import edu.bupt.mapper.SentenceMapper;
import edu.bupt.model.Sentence;
import edu.bupt.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by shixu on 2016-07-30.
 */
public class SentenceMapperImpl implements SentenceMapper {


    @Override
    public int addSentenceBatch(List<Sentence> sentences) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        SentenceMapper sentenceMapper = session.getMapper(SentenceMapper.class);
        int r = sentenceMapper.addSentenceBatch(sentences);
        session.commit();
        session.close();
        return r;
    }

    @Override
    public List<Sentence> getListOfSentence() {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        SentenceMapper sentenceMapper = session.getMapper(SentenceMapper.class);
        List<Sentence> sentences = sentenceMapper.getListOfSentence();
        session.close();
        return sentences;
    }

    @Override
    public int addSentence(Sentence sentence) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        SentenceMapper sentenceMapper = session.getMapper(SentenceMapper.class);
        int id = sentenceMapper.addSentence(sentence);
        session.commit();
        session.close();
        return id;
    }
}
