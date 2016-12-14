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
    private static CommonMapper commonMapper;
    private News news;
    private Map<String, Integer> topicWordMap;
    private Map<String, Integer> tfMap;
    private Map<String, Map<String, Integer>> dfMap;
    private Sentence title;

    private int size = 4000;
    private static final int MIN_TF_IDF = 8;
    private static final int MIN_SENT_LENGTH = 16;


    public TopicSentExtractor(News news) {
        this.commonMapper = new CommonMapperImpl();
        this.dfMap = commonMapper.getDfMap(); //word -> document frequency
        this.topicWordMap = new HashMap<>();
        this.tfMap = new HashMap<>();
        this.news = news;
        this.title = new Sentence(news.getTitle());
    }

    public TreeMap<Double, Sentence> handler() {
        buildTopicWordSet();   //生成文章的主题词集合
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
        resultPrint(sentenceTreeMap);
        return sentenceTreeMap;
    }

    /**
     * 打印结果
     *
     * @param sentenceTreeMap
     */
    private void resultPrint(TreeMap<Double, Sentence> sentenceTreeMap) {
        System.out.println("##ID:" + news.getId() + "\t[" + news.getTitle() + "]");
        System.out.println("```\n" + news.getContent() + "\n```");
        int i = 0;
        System.out.println("###主题句");
        for (Map.Entry<Double, Sentence> entry :
                sentenceTreeMap.entrySet()) {
            if (i++ > 10)
                break;
            System.out.print("\n**" + entry.getValue().getSentenceContent() + "**");
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

    /**
     * 计算sentence的总权值
     *
     * @param sentence 句子对象
     * @return 权值结果
     * 初始权重： 0.2 * sentLenScore + 4 * similarity * alpha + 6 * neScore;
     */
    private double getFinalWeight(Sentence sentence) {
        int sentLenScore = sentence.getWordList().size() > MIN_SENT_LENGTH ? 1 : 0;
        double alpha;
        int titleWeight = getTitleValue();
        if (titleWeight >= 2)
            alpha = 1;
        else
            alpha = 0.1;
        double similarity = getSim(sentence, title);
        double neScore = (double) sentence.getEntityList().size() / sentence.getWordList().size();
        double finalWeight = 0.2 * sentLenScore + 4 * similarity * alpha + 6 * neScore;
        finalWeight = 1 / (1 + Math.pow(Math.E, -finalWeight));  //用sigmoid函数将值归一化到0-1取件
        return finalWeight;
    }

    /**
     * 通过余弦相似度法则 计算两个句子的相似度
     *
     * @param title    标题 把标题当作一个Sentence对象来处理
     * @param sentence 句子对象
     * @return 余弦值
     */
    public static double getSim(Sentence title, Sentence sentence) {
        if (title.getWordList() == null)
            SegmentHandler.xfYunPos(title);
        if (sentence.getWordList() == null)
            SegmentHandler.xfYunPos(sentence);
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
    private static boolean isIncluded(String wordCon, List<Word> wordList) {
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
     * @return 标题和主题词集合重复的词（动词、名词）的个数
     */
    private int getTitleValue() {
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
     * 生成当前文章的主题词集合
     */
    private void buildTopicWordSet() {
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
            int df = 1;
            if (dfMap.get(word) != null) {
                df = Integer.parseInt(String.valueOf(dfMap.get(word).get("df_value")));
            }
            int tf = entry.getValue();
            int tfIdf = tfIdf(tf, df, size);
            if (tfIdf > MIN_TF_IDF)
                topicWordMap.put(word, tfIdf);
        }

        System.out.println("-------------------主题词集合："+topicWordMap);

    }

    /**
     * 计算tf_idf值
     *
     * @param tf 词在当前文档中出现的次数
     * @param df 词在所有文档中出现的次数（每篇文章如果出现，只算一次）
     * @param n  文档总数
     * @return tf_idf值
     */
    private static int tfIdf(int tf, int df, int n) {
        return new Double(tf * (Math.log(n / (df + 1)))).intValue();
    }


    public static void main(String[] args) {
        int tf = 1;
        int df = 66;
        System.out.println(tfIdf(tf, df, 4000));
        System.out.println(getSim(new Sentence("通信 计算机 电影"),new Sentence("我不喜欢看电视，也不喜欢看电影")));
    }

}
