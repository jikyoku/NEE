package edu.bupt.mapper;

import java.util.List;

/**
 * Created by shixu on 2016/11/15.
 */
public interface SentenceTempMapper {
    void insertBatch(List<String> sentenceList);
}
