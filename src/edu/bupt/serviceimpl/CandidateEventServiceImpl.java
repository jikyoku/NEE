package edu.bupt.serviceimpl;

import edu.bupt.mapperimpl.CandidateEventMapperImpl;
import edu.bupt.mapper.CandidateEventMapper;
import edu.bupt.model.CandidateEvent;

import edu.bupt.service.CandidateEventService;

/**
 * Created by shixu on 2016-07-30.
 */
public class CandidateEventServiceImpl implements CandidateEventService {

    CandidateEventMapper dao = new CandidateEventMapperImpl();
    @Override
    public int addEvent(CandidateEvent event) {
        return dao.addEvent(event);
    }
}
