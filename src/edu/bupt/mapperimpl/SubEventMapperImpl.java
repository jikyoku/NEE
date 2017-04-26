package edu.bupt.mapperimpl;

import edu.bupt.mapper.SubEventMapper;
import edu.bupt.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by shixu on 2016-08-01.
 */
public class SubEventMapperImpl implements SubEventMapper {


    @Override
    public int getIdByName(String typeName) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        SubEventMapper subEventMapper = session.getMapper(SubEventMapper.class);
        int id = subEventMapper.getIdByName(typeName);
        session.close();
        return id;
    }
}
