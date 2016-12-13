package edu.bupt.main.ced;

import edu.bupt.main.segment.SegmentHandler;
import edu.bupt.mapper.CommonMapper;
import edu.bupt.mapperimpl.CommonMapperImpl;
import edu.bupt.model.Entity;
import edu.bupt.model.News;
import edu.bupt.model.Sentence;

import edu.bupt.model.Word;
import edu.bupt.util.TFIDFUtil;
import org.fnlp.nlp.pipe.TFIDF;
import org.junit.Test;

import java.security.Key;
import java.util.*;

/**
 * Created by shixu on 2016/12/5.
 * 抽取出一篇新闻的主题句
 */
public class TopicSentExtractor {
    private static Map<String, Map<String, Integer>> dfMap;
    private static CommonMapper commonMapper;
    private News news;
    private Map<String, Integer> topicWordMap;
    private Map<String, Integer> tfMap;
    private Sentence title;

    private int size = 4000;
    private static final int MIN_TF_IDF = 8;
    private static final int MIN_SENT_LENGTH = 16;

    public TopicSentExtractor(News news) {
        commonMapper = new CommonMapperImpl();
        dfMap = commonMapper.getDfMap();
        this.topicWordMap = new HashMap<>();
        tfMap = new HashMap<>();
        this.news = news;
        title = new Sentence(news.getTitle());
    }

    public void handler() {
        setTopicWordSet();
        TreeMap<Double, Sentence> sentenceTreeMap = new TreeMap<>(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return o2.compareTo(o1);
            }
        });
        for (Sentence sentence : news.getSentenceList()) {
            EntityExtractor.entityExtract(sentence); //抽取实体
            sentenceTreeMap.put(getFinalWeight(sentence), sentence); //将句子的权重放进map中
        }
        System.out.println("##ID:" + news.getId() + "\t[" + news.getTitle()+"]");
        System.out.println("```\n" + news.getContent() + "\n```");
        int i = 0;
        System.out.println("###主题句");
        for (Map.Entry<Double, Sentence> entry :
                sentenceTreeMap.entrySet()) {
            if (i++ > 10)
                break;
            System.out.print("\n**" + entry.getValue().getSentenceContent()+"**");
            System.out.print("\n>**权重：**" + entry.getKey() + "");
            System.out.print("\n**实体：**");
            for (Entity entity :
                    entry.getValue().getEntityList()) {
                System.out.print("\t" + entity.getEntityContent());
            }
            System.out.println();
        }
        System.out.println("---");
    }

    public double getFinalWeight(Sentence sentence) {
        int sentLenScore = sentence.getWordList().size() > MIN_SENT_LENGTH ? 1 : 0;
        double alpha;
        int titleWeight = getTitleValue();
        if (titleWeight >= 2)
            alpha = 1;
        else
            alpha = 0.1;
        double similarity = getSim(sentence, title);
        double neScore = (double) sentence.getEntityList().size() / sentence.getWordList().size();
        return 0.2 * sentLenScore + 4 * similarity * alpha + 6 * neScore;
    }

    /**
     * 通过余弦相似度法则 计算两个句子的相似度
     *
     * @param title    标题
     * @param sentence 句子
     * @return 余弦值
     */
    public double getSim(Sentence title, Sentence sentence) {
        Set<String> wordSet = new HashSet<>();
        for (Word word :
                title.getWordList()) {
            wordSet.add(word.getCont());
        }
        for (Word word :
                sentence.getWordList()) {
            wordSet.add(word.getCont());
        }
        int[] a = new int[wordSet.size()];
        int[] b = new int[wordSet.size()];
        int i = 0;
        for (String wordCont :
                wordSet) {
            if (isIncluded(wordCont, title.getWordList()))
                a[i] = 1;
            if (isIncluded(wordCont, sentence.getWordList()))
                b[i] = 1;
            i++;
        }
        double fenZi = 0;
        double aSumOfSquare = 0;
        double bSumOfSquare = 0;
        double similarity;
        for (int j = 0; j < wordSet.size(); j++) {
            fenZi += a[j] * b[j];
            aSumOfSquare += a[j] * a[j];
            bSumOfSquare += b[j] * b[j];
        }
        similarity = fenZi / (Math.sqrt(aSumOfSquare) * Math.sqrt(bSumOfSquare));
        return similarity;
    }

    /**
     * 判断词列表中是否存在这个词
     *
     * @param wordCon
     * @param wordList
     * @return
     */
    public boolean isIncluded(String wordCon, List<Word> wordList) {
        for (Word word :
                wordList) {
            if (word.getCont().equals(wordCon))
                return true;
        }
        return false;
    }


    /**
     * 计算标题的可参考价值
     *
     * @return
     */
    public int getTitleValue() {
        SegmentHandler.xfYunPos(title);
        int titleWeight = 0;
        List<Word> wordList = title.getWordList();
        for (Word word :
                wordList) {
            if (word.getPos().contains("n") || word.getPos().contains("v"))
                if (topicWordMap.containsKey(word.getCont())) {
                    titleWeight++;
                }
        }
        return titleWeight;
    }


    /**
     * 生成当前新闻的主题词集合
     */
    @Test
    public void setTopicWordSet() {
        List<Sentence> sentenceList = news.getSentenceList();
        List<Word> wordList;
        for (Sentence sentObj :
                sentenceList) {
            wordList = sentObj.getWordList();
            for (Word word :
                    wordList) {
                if (tfMap.containsKey(word.getCont())) {
                    tfMap.put(word.getCont(), tfMap.get(word.getCont()) + 1);
                } else {
                    tfMap.put(word.getCont(), 1);
                }
            }
        }
        for (Map.Entry<String, Integer> entry :
                tfMap.entrySet()) {
            String word = entry.getKey();
            if (dfMap.get(word) == null) {
                continue;
            }
            int tf = entry.getValue();
            int df = Integer.parseInt(String.valueOf(dfMap.get(word).get("df_value")));
            int tfIdf = tfIdf(tf, df, size);
            if (tfIdf > MIN_TF_IDF)
                topicWordMap.put(word, tfIdf);
        }
    }

    /**
     * 计算tf idf
     *
     * @param tf 词在当前文档中出现的次数
     * @param df 词在所有文档中出现的次数（每篇文章如果出现，只算一次）
     * @param n  文档总数
     * @return tf_idf值
     */
    public static int tfIdf(int tf, int df, int n) {
        return new Double(tf * (Math.log(n / (df + 1)))).intValue();
    }


    public static void main(String[] args) {
        int tf = 1;
        int df = 66;
        System.out.println(tfIdf(tf, df, 4000));
    }

}
