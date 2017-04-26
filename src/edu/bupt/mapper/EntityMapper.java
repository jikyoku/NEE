package edu.bupt.mapper;

import edu.bupt.model.Entity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by shixu on 2016-07-29.
 */
public interface EntityMapper {

    /**
     * 批量添加实体对象到数据库中，使用sql拼接，可以提高添加速度。
     *
     * @param list
     * @return int ()
     *
     * */
    int addEntityBatch(List<Entity> list);


    /**
     * 测试基于注解的方式定义sql，通过id查询实体Entity对象
     *
     * @param id
     * @return Entity
     *
     * */
    @Select("select * from entity where  entityId = #{id}")
    Entity getEntityById(int id);

}
