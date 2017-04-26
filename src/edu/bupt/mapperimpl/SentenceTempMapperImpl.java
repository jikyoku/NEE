package edu.bupt.mapperimpl;

import edu.bupt.mapper.SentenceTempMapper;
import edu.bupt.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by shixu on 2016/11/15.
 *
 */
public class SentenceTempMapperImpl implements SentenceTempMapper {
    @Override
    public void insertBatch(List<String> sentenceList) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        SentenceTempMapper sentenceTempMapper = session.getMapper(SentenceTempMapper.class);
        sentenceTempMapper.insertBatch(sentenceList);
        session.commit();
        session.close();
    }
}
