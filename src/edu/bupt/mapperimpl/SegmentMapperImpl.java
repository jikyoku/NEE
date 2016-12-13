package edu.bupt.mapperimpl;

import edu.bupt.mapper.SegmentMapper;
import edu.bupt.model.Segment;
import edu.bupt.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by shixu on 2016-07-30.
 */
public class SegmentMapperImpl implements SegmentMapper {

    @Override
    public int addSegmentBatch(List<Segment> segments) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        SegmentMapper segmentMapper = session.getMapper(SegmentMapper.class);
        int r = segmentMapper.addSegmentBatch(segments);
        session.commit();
        session.close();
        return r;
    }



    @Override
    public List<Integer> getListOfId() {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        SegmentMapper segmentMapper = session.getMapper(SegmentMapper.class);
        List<Integer> ids = segmentMapper.getListOfId();
        session.close();
        return ids;
    }

    @Override
    public Segment getSegmentById(int id) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        SegmentMapper segmentMapper = session.getMapper(SegmentMapper.class);
        Segment segment = segmentMapper.getSegmentById(id);
        session.close();
        return segment;
    }
}
