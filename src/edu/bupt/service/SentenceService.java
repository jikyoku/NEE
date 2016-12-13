package edu.bupt.service;

import edu.bupt.mapper.SentenceMapper;
import edu.bupt.model.Sentence;

import java.util.List;

/**
 * Created by shixu on 2016-07-30.
 */
public interface SentenceService  {
    int addSentenceBatch(List<Sentence> sentences);
    List<Sentence> getListOfSentence();
    int addSentence(Sentence sentence);
}
