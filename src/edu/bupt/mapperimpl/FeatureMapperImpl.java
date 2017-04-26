package edu.bupt.mapperimpl;

import edu.bupt.mapper.FeatureMapper;
import edu.bupt.model.Feature;
import edu.bupt.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by shixu on 2016-07-31.
 */
public class FeatureMapperImpl implements FeatureMapper {


    @Override
    public void addFeature(Feature feature) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        FeatureMapper featureMapper = session.getMapper(FeatureMapper.class);
        featureMapper.addFeature(feature);
        session.commit();
        session.close();
    }
}
