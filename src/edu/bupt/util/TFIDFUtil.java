package edu.bupt.util;

import edu.bupt.http.HttpRequest;
import edu.bupt.main.preprocess.PreProcess;
import edu.bupt.main.segment.SegmentHandler;
import edu.bupt.mapper.CommonMapper;
import edu.bupt.mapper.TFIDFWordMapper;
import edu.bupt.mapperimpl.CommonMapperImpl;
import edu.bupt.mapperimpl.TFIDFWordMapperImpl;
import edu.bupt.model.News;
import edu.bupt.model.Sentence;
import edu.bupt.model.TFIDFWord;
import edu.bupt.service.NewsService;
import edu.bupt.serviceimpl.NewsServiceImpl;
import org.junit.Test;

import java.util.*;

import edu.bupt.model.Word;

/**
 * Created by shi xu on 2016/11/10.
 */
public class TFIDFUtil {
    private static NewsService newsService;
    private static HashMap<String, Integer> dfMap = new HashMap<>();
    private static List<String> newsList = null;
    private static TFIDFWordMapper tfidfWordMapper = null;


    static {
        newsService = new NewsServiceImpl();
        tfidfWordMapper = new TFIDFWordMapperImpl();
        newsList = newsService.getNewsList(5001, null);
    }
    private void insertDfMap() {
        System.out.println("开始插入：");
        int i = 0;
        int bufferSize = 1000;
        List<TFIDFWord> tfidfWords = new ArrayList<>(bufferSize);
        for (Map.Entry<String, Integer> entry : dfMap.entrySet()) {
            TFIDFWord tfidfWord = new TFIDFWord();
            tfidfWord.setDfCount(entry.getValue());
            tfidfWord.setWord(entry.getKey());
            tfidfWords.add(tfidfWord);
            if (tfidfWords.size() >= bufferSize) {
                tfidfWordMapper.insertDF(tfidfWords);
                tfidfWords.clear();
                System.out.println("还剩" + (dfMap.size() - (++i) * bufferSize) + "条---");
            }
        }
        tfidfWordMapper.insertDF(tfidfWords);
    }

    private void buildDfMap() {
        int i = 0;

        String[] poses = {"n", "v"};
        for (String pos :
                poses) {
            for (String newsCont :
                    newsList) {
                newsCont = PreProcess.clean(newsCont);
                System.out.println("news id:" + (i++));
                List<Sentence> sentenceList = PreProcess.newsSeparate(newsCont, PreProcess.END_LABEL);
                for (Sentence sentence :
                        sentenceList) {
                    if ("".equals(sentence.getSentenceContent().trim()))
                        continue;
                    SegmentHandler.xfYunPos(sentence);
                    List<Word> wordList = sentence.getWordList();
                    for (Word word :
                            wordList) {
                        if (word.getPos().contains(pos)) {
                            dfMap.put(word.getCont(), 0);
                        }
                    }
                }
            }
        }
        //统计词的文档频率（df）
        i = 0;
        for (Map.Entry<String, Integer> wordEntry :
                dfMap.entrySet()) {
            int count = 0;
            for (String news :
                    newsList) {
                if (news.contains(wordEntry.getKey()))
                    count++;
            }
            System.out.println("word id:" + (i++));
            dfMap.put(wordEntry.getKey(), count);
        }
    }

    @Test
    public void handler() {
        buildDfMap();
        insertDfMap();
    }
}
