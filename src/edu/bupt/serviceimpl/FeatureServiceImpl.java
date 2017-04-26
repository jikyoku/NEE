package edu.bupt.serviceimpl;

import edu.bupt.mapper.FeatureMapper;
import edu.bupt.mapperimpl.FeatureMapperImpl;
import edu.bupt.model.Feature;
import edu.bupt.service.FeatureService;

/**
 * Created by shixu on 2016-07-31.
 */
public class FeatureServiceImpl implements FeatureService {

    FeatureMapper featureMapper = new FeatureMapperImpl();
    @Override
    public void addFeature(Feature feature) {

        featureMapper.addFeature(feature);
    }
}
