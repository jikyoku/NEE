package edu.bupt.mapper;

import edu.bupt.model.Segment;

import java.util.List;

/**
 * Created by shixu on 2016-07-25.
 */
public interface SegmentMapper {
    /**
     * 批量添加分词后的对象列表
     *
     * @param list
     * @return int
     * */
    int addSegmentBatch(List<Segment> list);

    /**
     * 获取所有分词对象的id列表
     *
     * @param null
     * @return list
     * */
    List<Integer> getListOfId();

    /**
     * 通过id获取segment对象
     *
     * @param id
     * @return Segment
     * */
    Segment getSegmentById(int id);
}
