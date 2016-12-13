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
    private static HashMap<String, Integer> tfMap = new HashMap<>();
    private static HashMap<String, Integer> dfMap = new HashMap<>();
    private static List<String> newsList = null;
    private static TFIDFWordMapper tfidfWordMapper = null;
    private static int numOfNews = 50;

    static {
        newsService = new NewsServiceImpl();
        tfidfWordMapper = new TFIDFWordMapperImpl();
        newsList = newsService.getNewsList(5001, numOfNews);
    }

    private void initTfMap() {
        newsList = newsService.getNewsList();
        int h = 1;
        for (String news :
                newsList) {
            System.out.println("news id:" + (h++));
            news = news.replaceAll("^[　*| *]*", "").replaceAll("[　*| *]*$", "");
            Sentence sentence = new Sentence();
            sentence.setSentenceContent(news);
            SegmentHandler.ansjSeg(sentence);
            String[] words = sentence.getWords();
            String[] tags = sentence.getTaggers();


            for (int i = 0; i < tags.length; i++) {
                if (tags[i].equals("v")) {
                    //int countInNews = getCountInNews(words[i], words);
                    if (!tfMap.containsKey(words[i]))
                        tfMap.put(words[i], 1);
                    else
                        tfMap.put(words[i], tfMap.get(words[i]) + 1);
                }
            }
        }
        System.out.println(tfMap);
        System.out.println(tfMap.size());
    }

    private void insertTfMap() {
        List<TFIDFWord> tfidfWords = new ArrayList<>(tfMap.size());
        for (Map.Entry<String, Integer> entry : tfMap.entrySet()) {
            TFIDFWord tfidfWord = new TFIDFWord();
            tfidfWord.setTfCount(entry.getValue());
            tfidfWord.setWord(entry.getKey());
            tfidfWords.add(tfidfWord);
            if (tfidfWords.size() > 1000) {
                tfidfWordMapper.insertTF(tfidfWords);
                tfidfWords.clear();
            }
        }
        tfidfWordMapper.insertTF(tfidfWords);
    }

    private void insertDfMap() {
        System.out.println("开始插入：");
        int i = 0;
        int size = 1000;
        List<TFIDFWord> tfidfWords = new ArrayList<>(size);
        for (Map.Entry<String, Integer> entry : dfMap.entrySet()) {
            TFIDFWord tfidfWord = new TFIDFWord();
            tfidfWord.setDfCount(entry.getValue());
            tfidfWord.setWord(entry.getKey());
            tfidfWords.add(tfidfWord);
            if (tfidfWords.size() >= size) {
                tfidfWordMapper.insertDF(tfidfWords);
                tfidfWords.clear();
                System.out.println("还剩" + (dfMap.size() - (++i) * size) + "条---");
            }
        }
        tfidfWordMapper.insertDF(tfidfWords);
    }

    public void initDfMap() {
        int i = 0;
        String[] poses = {"n", "v"};
        for (String pos :
                poses) {
            for (String news :
                    newsList) {
                news = PreProcess.clean(news);
                System.out.println("news id:" + (i++));
                List<Sentence> sentenceList = PreProcess.newsSeparate(news, PreProcess.END_LABEL);
                //  Sentence sentence = new Sentence(news); //把一篇新闻当作一个句子处理。
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

            System.out.println("------------词性" + pos + "个数：" + dfMap.size());
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
    public void test() {
        initDfMap();
        insertDfMap();
    }
}
