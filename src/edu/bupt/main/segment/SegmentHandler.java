package edu.bupt.main.segment;

import edu.bupt.http.HttpRequest;
import edu.bupt.model.Sentence;
import edu.bupt.util.json.JsonParser;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.recognition.impl.FilterRecognition;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.json.JSONArray;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.bupt.model.Word;

/**
 * Created by shixu on 2016-07-25.
 */
public class SegmentHandler {


    /**
     * ansj分词器 有过滤器
     *
     * @param sentence 句子
     * @param filter   过滤器
     * @return 分词之后的sentence对象
     */
    public static Sentence ansjPos(Sentence sentence, FilterRecognition filter) {
        List<Term> terms = ToAnalysis.parse(sentence.getSentenceContent()).recognition(filter).getTerms();
        List<Word> wordList = new ArrayList<>(terms.size());
        for (int i = 0; i < terms.size(); i++) {
            Word word = new Word();
            word.setId(i);  //句子中的位置
            word.setCont(terms.get(i).getName());  //词的内容
            word.setPos(terms.get(i).getNatureStr()); //词的词性标注
            wordList.add(word);
        }
        sentence.setWordList(wordList);
        return sentence;
    }


    /**
     * ansj分词 无过滤器
     *
     * @param sentence
     * @return
     */
    public static Sentence ansjPos(Sentence sentence) {
        List<Term> terms = BaseAnalysis.parse(sentence.getSentenceContent()).getTerms();
        List<Word> wordList = new ArrayList<>(terms.size());
        for (int i = 0; i < terms.size(); i++) {
            Word word = new Word();
            word.setId(i);  //句子中的位置
            word.setCont(terms.get(i).getName());  //词的内容
            word.setPos(terms.get(i).getNatureStr()); //词的词性标注
            wordList.add(word);
        }
        sentence.setWordList(wordList);
        return sentence;
    }

    /**
     * 调用讯飞的分词api
     *
     * @param sentence
     * @return
     */
    public static Sentence xfYunPos(Sentence sentence) {
        String jsonStr = HttpRequest.xfyunHttpApi(sentence.getSentenceContent(), HttpRequest.PATTERN_SRL, HttpRequest.FORMAT_JSON);
        JSONArray jsonArray = JsonParser.getJsonArrayByStr(jsonStr);
        List<Word> wordList = JsonParser.parseJsonArrayToWords(jsonArray);
        sentence.setWordList(wordList);
        return sentence;
    }


    @Test
    public void testSeg() {
        Sentence sentence = new Sentence();
        sentence.setSentenceContent("数据计算平台搭建，基础算法实现，当然，要求支持大样本量、高维度数据，所以可能还需要底层开发、并行计算、分布式计算等方面的知识；");
        System.out.println("ansj的分词结果：-----------");
        ansjPos(sentence);
        System.out.println(sentence.getWordList());
        System.out.println("科大讯飞的分词结果：-----------");
        xfYunPos(sentence);
        System.out.println(sentence.getWordList());
    }



    /**
     * @param words
     */
    static void addUserDefineDict(List<String> words) {
        String label = "";
        int freq = 100;
        for (String word :
                words) {
            UserDefineLibrary.insertWord(word, label, freq);
        }
    }


    /**
     * 过滤器
     *
     * @return
     */
    public static FilterRecognition getMyFilter() {
        FilterRecognition filter = new FilterRecognition();
        filter.insertStopNatures("uj"); //过滤词性
        filter.insertStopNatures("ul");
        filter.insertStopNatures("null");
        filter.insertStopWord("我和你", "七日"); //过滤单词
        filter.insertStopRegex("/w"); //支持正则表达式
        return filter;
    }
}
