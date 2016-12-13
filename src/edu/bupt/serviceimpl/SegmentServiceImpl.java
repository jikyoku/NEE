package edu.bupt.serviceimpl;

import edu.bupt.mapper.SegmentMapper;
import edu.bupt.mapperimpl.SegmentMapperImpl;
import edu.bupt.model.Segment;
import edu.bupt.service.SegmentService;

import java.util.List;

/**
 * Created by shixu on 2016-07-30.
 */
public class SegmentServiceImpl implements SegmentService {

    SegmentMapper segmentMapper = new SegmentMapperImpl();

    @Override
    public int addSegmentBatch(List<Segment> segments) {
        return segmentMapper.addSegmentBatch(segments);
    }



    @Override
    public List<Integer> getListOfId() {
        return segmentMapper.getListOfId();
    }

    @Override
    public Segment getSegmentById(int id) {
      return segmentMapper.getSegmentById(id);
    }
}
