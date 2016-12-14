package edu.bupt.mapper;

import edu.bupt.model.News;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;

import java.util.List;
import java.util.Map;

/**
 * Created by shixu on 2016-07-25.
 */
public interface NewsMapper {
    /**
     * 添加一条新闻到数据库中
     *
     * @param news
     * @return int (返回自增ID)
     */
    int addNews(News news);

    /**
     * 获取所有新闻的id列表
     *
     * @param null
     * @return list(id列表)
     */
    List<Integer> getIdList();

    /**
     * 通过id得到新闻内容
     *
     * @param id
     * @return String(新闻内容)
     */
    String getNewsById(int id);

    /**
     * @param startId 从这条id开始
     * @param n       步长
     */
    @Select("select content from news where id >#{id} order by id limit #{n} ")
    List<String> getNews(@Param("id") int startId, @Param("n") int n);


    @MapKey("id")
    Map<Integer, News> getNewsMap(@Param("startId") Integer startId,@Param("endId") Integer endId, @Param("length") Integer length);

    @Select("select count(*) from news")
    int getNewsCount();

    List<String> getNewsList(@Param("startId") Integer startId, @Param("length") Integer length);
}
