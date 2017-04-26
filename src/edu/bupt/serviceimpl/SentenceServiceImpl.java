package edu.bupt.serviceimpl;

import edu.bupt.mapper.SentenceMapper;
import edu.bupt.mapperimpl.SentenceMapperImpl;
import edu.bupt.model.Sentence;
import edu.bupt.service.SentenceService;

import java.util.List;

/**
 * Created by shixu on 2016-07-30.
 */
public class SentenceServiceImpl implements SentenceService {

    SentenceMapper sentenceMapper = new SentenceMapperImpl();

    @Override
    public int addSentenceBatch(List<Sentence> sentences) {
       return sentenceMapper.addSentenceBatch(sentences);
    }
    @Override
    public List<Sentence> getListOfSentence() {
       return sentenceMapper.getListOfSentence();
    }

    @Override
    public int addSentence(Sentence sentence) {
        return sentenceMapper.addSentence(sentence);
    }
}
