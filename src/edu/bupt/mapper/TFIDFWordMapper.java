package edu.bupt.mapper;

import edu.bupt.model.TFIDFWord;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by shixu on 2016/11/15.
 */
public interface TFIDFWordMapper {
    void insertTF(List<TFIDFWord> tfidfWords);

    List<String> getWords();

    void insertDF(List<TFIDFWord> tfidfWords);

    void insertTFIDF(List<TFIDFWord> tfidfWords);

    @Select("select word from tf_idf_word")
    List<String> getTFIDFWords();
}
