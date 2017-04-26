package edu.bupt.mapperimpl;

import edu.bupt.mapper.EntityMapper;
import edu.bupt.model.Entity;
import edu.bupt.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by shixu on 2016-07-30.
 */
public class EntityMapperImpl implements EntityMapper {
    @Override
    public int addEntityBatch(List<Entity> list) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        EntityMapper entityMapper = session.getMapper(EntityMapper.class);
        int r = entityMapper.addEntityBatch(list);
        session.commit();
        session.close();
        return r;
    }



    @Override
    public Entity getEntityById(int id) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        EntityMapper entityMapper = session.getMapper(EntityMapper.class);
        Entity entity = entityMapper.getEntityById(id);
        session.close();
        return entity;
    }
}
