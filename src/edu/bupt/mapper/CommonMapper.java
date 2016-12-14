package edu.bupt.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shixu on 2016/12/5.
 */
public interface CommonMapper {
    @MapKey("word")
    Map<String,Map<String,Integer>> getDfMap();

    @Select("select word,df_value from df_word")
    @MapKey("word")
    Map<String,Map<String,Object>> getDFMap();
    void insertWord(List<String> wordList);

    @Select("select coreSent from forshixu where title = #{title}")
    String getCoreSentByTitle(String title);
}
