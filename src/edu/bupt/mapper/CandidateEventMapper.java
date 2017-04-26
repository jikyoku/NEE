package edu.bupt.mapper;

import edu.bupt.model.CandidateEvent;

/**
 * Created by shi xu on 2016-07-28.
 * @author shixu
 */
public interface CandidateEventMapper {
    /**
     * 增加一个候选事件对象到数据库中
     *
     * @param event
     * @return int （返回自增的主键）
     *
     * */
    int addEvent(CandidateEvent event);
}
