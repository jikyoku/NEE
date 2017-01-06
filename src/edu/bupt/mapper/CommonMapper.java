package edu.bupt.mapper;

import edu.bupt.model.TFIDFWord;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shixu on 2016/12/5.
 */
public interface CommonMapper {
    @MapKey("word")
    Map<String, Map<String, Integer>> getDfMap();

    @Select("select word,df_value from df_word")
    @MapKey("word")
    Map<String, Map<String, Object>> getDFMap();

    void insertWord(List<String> wordList);

    @Select("select coreSent from forshixu where title = #{title}")
    String getCoreSentByTitle(String title);

    @Select("select endid from log ORDER BY id DESC limit 1;")
    Integer getLogEndId();

    @Insert("insert into log (startId,endId) values(#{startId},#{endId})")
    void insertNewLog(@Param("startId") Integer startId, @Param("endId") Integer endId);

    void insertDF(Map<String, Map<String, Integer>> params);

    @Update("update df_word set df_value = #{df} where word = #{word}")
    void updateDfOne(@Param("word") String word, @Param("df") Integer df);
    @Update("insert into  df_word (word,df_value) value(#{word},#{df})")

    void insertDFOne(@Param("word") String word, @Param("df") Integer df);
}
