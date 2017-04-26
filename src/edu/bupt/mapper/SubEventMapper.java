package edu.bupt.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * Created by shi xu on 2016-08-01.
 *
 */
public interface SubEventMapper {
    /**
     * 通过SubEvent的内容获取id
     *
     * @param typeName 类型名称
     * @return int 返回id
     * */
    int getIdByName(@Param(value = "heheda") String typeName);
}
