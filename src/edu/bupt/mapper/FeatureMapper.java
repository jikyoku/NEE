package edu.bupt.mapper;

import edu.bupt.model.Feature;

/**
 * Created by shi xu on 2016-07-31.
 */
public interface FeatureMapper {

    /**
     * 添加一个特征Feature对象到数据库中
     *
     * @param feature
     * return null
     * */
    void addFeature(Feature feature);
}
