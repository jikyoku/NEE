package edu.bupt.serviceimpl;

import edu.bupt.mapper.SubEventMapper;
import edu.bupt.mapperimpl.SubEventMapperImpl;
import edu.bupt.service.SubEventService;

/**
 * Created by shixu on 2016-08-01.
 */
public class SubEventServiceImpl implements SubEventService {

    SubEventMapper subEventMapper = new SubEventMapperImpl();
    @Override
    public int getIdByName(String typeName) {
        return subEventMapper.getIdByName(typeName);
    }
}
