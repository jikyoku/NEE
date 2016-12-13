package edu.bupt.mapper;
import edu.bupt.model.TriggerWord;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by shi xu on 2016-07-28.
 * @author shixu
 */
public interface TriggerWordMapper {
    /**
     * 得到触发词实体类的列表
     * @param null
     * @return list
     * */
    List<TriggerWord> getListOfTriggerWord();


    /**
     * 得到一个map，键为触发词内容，值为触发词id
     *
     * @param null
     * @return map
     * */
    @Select("select triggerwordid,triggerwordcontent from triggerwords")
    @MapKey("triggerWordContent")
    Map<String,TriggerWord> getMapOfTriggerWord();

    /**
     * 将一个触发词实体类写入到到数据库中
     *
     * @param triggerWord
     * @return int(返回自增的主键id)
     * */
    int addTrgWd(TriggerWord triggerWord);


    /**
     * 这个方法的返回值会连同触发词对应的子事件SubEvent实体对象一同返回，SubEvent被注入到TriggerWord中去
     *
     * @param null
     * @return List
     * */
    List<TriggerWord> getTwAndSubType();

    /**
     * 通过触发词内容得到触发词对象,z之所以是返回list,是因为sql中使用了模糊查询，详细请看TriggerWord.xml中配置
     *
     * @param value
     * @return List
     *
     * */
    List<TriggerWord> getTWByName(String value);
}
