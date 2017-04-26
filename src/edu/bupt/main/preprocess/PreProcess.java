package edu.bupt.main.preprocess;

import edu.bupt.main.segment.SegmentHandler;
import edu.bupt.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * Created by shi xu on 2016-07-26.
 * 预处理
 * 1. 分词
 * 2. 分句
 * 3. 去除无用标签
 */
public class PreProcess {
    public static final String END_LABEL = "。|！|？|\\?";

    /**
     * 清理字符串
     *
     * @param str 待清理字符串
     * @return 清理后的字符串
     */
    public static String clean(String str) {
        String regex = "<\\w+\\s*/?>|\\s|●|\\s|^?(&|★|#|$|%|)";
        return str.replaceAll(regex, "").replaceAll("^[　*| *]*", "").replaceAll("[　*| *]*$", "");
    }

    /**
     * 对新闻进行分句
     *
     * @param news  新闻字符串
     * @param regex 句子结束符号
     * @return 句子列表
     */
    public static List<Sentence> split(String news, String regex) {
        Pattern pattern = Pattern.compile(regex);
        String[] sentences = pattern.split(news);
        List<Sentence> sentenceList = new ArrayList<>(sentences.length);
        for (String senCont :
                sentences) {
            sentenceList.add(new Sentence(clean(senCont)));
        }
        return sentenceList;
    }

    /**
     * 批量分词
     *
     * @param newsMap 新闻集合
     */
    public static void segment(TreeMap<Integer, News> newsMap) {
        for (Map.Entry<Integer, News> newsEntry :
                newsMap.entrySet()) {
            News newsObj = newsEntry.getValue();
            segment(newsObj);
        }
    }


    /**
     * 对一篇新闻分词
     *
     * @param newsObj 新闻对象
     */
    public static void segment(News newsObj) {
        newsObj.setTitleSent(new Sentence(newsObj.getTitle()));
        List<Sentence> sentenceList = PreProcess.split(newsObj.getContent(), PreProcess.END_LABEL);
        newsObj.setSentenceList(sentenceList);
        newsObj.setTitleSent(SegmentHandler.segment(newsObj.getTitleSent()));
        for (Sentence sentence : sentenceList) {
            SegmentHandler.segment(sentence);
        }
    }

}