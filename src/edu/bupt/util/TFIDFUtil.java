package edu.bupt.util;

import edu.bupt.main.preprocess.PreProcess;
import edu.bupt.main.segment.SegmentHandler;
import edu.bupt.mapper.CommonMapper;
import edu.bupt.model.News;
import edu.bupt.model.Sentence;
import org.junit.Test;

import java.util.*;

import edu.bupt.model.Word;

/**
 * Created by shi xu on 2016/11/10.
 * tf idf
 */
public class TFIDFUtil {
    private HashMap<String, Integer> dfMap;
    private TreeMap<Integer, News> newsMap;
    private CommonMapper commonMapper;

    public TFIDFUtil(TreeMap<Integer, News> newsMap,CommonMapper commonMapper) {
        this.newsMap = newsMap;
        this.commonMapper = commonMapper;
        this.dfMap = new HashMap<>();
    }

    @Test
    public void handler() {
        buildDfMap();
        insertDfMap();
    }


    private void insertDfMap() {
        Map<String, Map<String, Integer>> param = new HashMap<>();
        param.put("keys", dfMap);
        commonMapper.insertDF(param);
    }


    private void buildDfMap() {
        String[] poses = {"n", "v"};
        String newsCont;
        for (Map.Entry<Integer, News> newsEntry : newsMap.entrySet()) {
            newsCont = PreProcess.clean(newsEntry.getValue().getContent());
            News newsObj = newsEntry.getValue();
            newsObj.setTitleSent(new Sentence(newsObj.getTitle()));
            List<Sentence> sentenceList = PreProcess.split(newsCont, PreProcess.END_LABEL);
           // sentenceList.add(newsObj.getTitleSent()); //把标题当作句子也要处理
            newsObj.setSentenceList(sentenceList);
            newsObj.setTitleSent(SegmentHandler.segment(newsObj.getTitleSent()));
            for (Sentence sentence :
                    sentenceList) {
                if ("".equals(sentence.getSentenceContent().trim()))
                    continue;
                SegmentHandler.segment(sentence);
                List<Word> wordList = sentence.getWordList();
                for (Word word :
                        wordList) {
                    for (String pos :
                            poses) {
                        if (word.getPos().contains(pos)) {
                            dfMap.put(word.getCont(), 0);
                            break;
                        }
                    }
                }
            }
            for (Word word :
                        newsObj.getTitleSent().getWordList()) {
                    for (String pos :
                            poses) {
                        if (word.getPos().contains(pos)) {
                            dfMap.put(word.getCont(), 0);
                            break;
                        }
                    }
                }
        }
        //统计词的文档频率（df）
        int i = 0;
        for (Map.Entry<String, Integer> wordEntry :
                dfMap.entrySet()) {
            int count = 0;
            for (Map.Entry<Integer, News> newsEntry : newsMap.entrySet()) {
                if (newsEntry.getValue().getContent().contains(wordEntry.getKey()))
                    count++;
            }
            System.out.println("word id:" + (i++));
            dfMap.put(wordEntry.getKey(), count);
        }
    }
}
