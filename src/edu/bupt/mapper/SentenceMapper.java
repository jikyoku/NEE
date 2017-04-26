package edu.bupt.mapper;

import edu.bupt.model.Sentence;

import java.util.List;

/**
 * Created by shixu on 2016-07-25.
 */
public interface SentenceMapper {

    /**
     * 批量添加sentence对象
     *
     * @param sentences
     * @return int
     * */
    int addSentenceBatch(List<Sentence> sentences);

    /**
     * 获取sentence对象列表
     *
     * @param null
     * @return list
     * @since 2016
     * @see Sentence
     * */
    List<Sentence> getListOfSentence();


    int addSentence(Sentence sentence);
}
