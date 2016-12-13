package edu.bupt.mapperimpl;

import edu.bupt.mapper.CandidateEventMapper;
import edu.bupt.model.CandidateEvent;
import edu.bupt.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by shixu on 2016-07-30.
 *
 */
public class CandidateEventMapperImpl implements CandidateEventMapper {

    @Override
    public int addEvent(CandidateEvent event) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        CandidateEventMapper candidateEventMapper = session.getMapper(CandidateEventMapper.class);
        int r = candidateEventMapper.addEvent(event);
        session.commit();
        session.close();
        return r;
    }
}
