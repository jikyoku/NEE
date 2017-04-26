package edu.bupt.serviceimpl;

import edu.bupt.mapper.EntityMapper;
import edu.bupt.mapperimpl.EntityMapperImpl;
import edu.bupt.model.Entity;
import edu.bupt.service.EntityService;

import java.util.List;

/**
 * Created by shixu on 2016-07-30.
 */
public class EntityServiceImpl implements EntityService {
    EntityMapper entityMapper = new EntityMapperImpl();

    @Override
    public int addEntityBatch(List<Entity> list) {
       return entityMapper.addEntityBatch(list);
    }


    @Override
    public Entity getEntityById(int id) {
        return entityMapper.getEntityById(id);
    }
}
