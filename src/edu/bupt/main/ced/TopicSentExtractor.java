package edu.bupt.main.ced;

import edu.bupt.main.segment.SegmentHandler;
import edu.bupt.mapper.CommonMapper;
import edu.bupt.mapper.TriggerWordMapper;
import edu.bupt.mapperimpl.CommonMapperImpl;
import edu.bupt.mapperimpl.TriggerWordMapperImpl;
import edu.bupt.model.*;
import java.util.*;

/**
 * Created by shi xu on 2016/12/5.
 * 抽取出一篇新闻的主题句 主题词
 */
public class TopicSentExtractor {
    private static CommonMapper commonMapper = new CommonMapperImpl();
    private static TriggerWordMapper triggerWordMapper = new TriggerWordMapperImpl();
    private static Map<String, TriggerWord> triggerWordMap = triggerWordMapper.getMapOfTriggerWord();
    public static int size = commonMapper.getSize();
    private static final int MIN_TF_IDF = 10;
    private static final int MIN_SENT_LENGTH = 16;
    private News news;  //待处理新闻
    private Map<String, Integer> topicWordMap;  //主题词集合
    private Map<String, Integer> tfMap;  //词频e
    private Map<String, Map<String, Integer>> dfMap;  //文档频
    private Map<String, Integer> tfidfMap;
    private double titleScore = 0;

    public Map<String, Integer> getTopicWordMap() {
        return topicWordMap;
    }

    public TopicSentExtractor(News news) {
        this.dfMap = commonMapper.getDfMap(); //word -> document frequency
        this.topicWordMap = new HashMap<>();
        this.tfMap = new HashMap<>();
        this.news = news;
        this.tfidfMap = new HashMap<>();
        size++;
        buildTopicWordSet();
        this.titleScore = getTitleScore();
    }

    /**
     * 获取主题句
     *
     * @return 主题句集合
     */
    public TreeMap<Double, Sentence> getTopicSentences() {
        TreeMap<Double, Sentence> sentenceTreeMap = new TreeMap<>(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return o2.compareTo(o1);
            }
        });
        for (Sentence sentence : news.getSentenceList()) {
            EntityExtractor.entityExtract(sentence); //抽取实体
            double scores = getFinalScores(sentence);
            if (sentenceTreeMap.size() >= 3) { //只取得分最高的前三句话
                Map.Entry<Double, Sentence> lastEntry = sentenceTreeMap.lastEntry();
                if (lastEntry.getKey() < scores) {
                    sentenceTreeMap.remove(lastEntry.getKey());
                    sentenceTreeMap.put(getFinalScores(sentence), sentence); //将句子的权重放进map中
                }
            } else {
                sentenceTreeMap.put(getFinalScores(sentence), sentence);
            }
        }
        //resultPrint(sentenceTreeMap); //打印结果
        return sentenceTreeMap;
    }

    /**
     * 计算sentence的总权值
     *
     * @param sentence 句子对象
     * @return 权值结果
     * 初始权重： 0.2 * sentLenScore + 4 * similarity * alpha + 6 * neScore;
     */
    @SuppressWarnings("unused")
	private double getFinalScores(Sentence sentence) {
        int sentLenScore = sentence.getWordList().size() > MIN_SENT_LENGTH ? 1 : 0; //句子长度权重
        double sentTfidfScore = getAllSentTfidf(sentence);  //所有词的tf-idf累加和
        //double postionScore = getPosition();  //句子在文中位置权重
//        Set<String> ttSet = getTriggerWordScores(sentence);  //触发词权重
        double alpha;
        if (titleScore >= 2)
            alpha = 1;
        else
            alpha = 0.1;
        double similarity = getSim(sentence, news.getTitleSent());  //句子与标题相似度
        double neScore = (double) getNerScore(sentence) / sentence.getWordList().size();  //实体贡献权重
        //double finalWeight = 0.6 * sentLenScore +  0.3 * sentTfidfScore + 70 * similarity * alpha + 4* neScore ;  //最终得分
        double finalWeight = 0.2 * sentLenScore + 4 * similarity * alpha + 6 * neScore;
        return sigmoid(finalWeight);
    }

    /**
     * 计算句子中实体种类个数
     *
     * @param sentence 句子
     * @return 实体种类个数
     */
    private int getNerScore(Sentence sentence) {
        int[] flag = new int[5];
        List<Entity> entities = sentence.getEntityList();
        for (Entity entity : entities) {
            if (flag[entity.getEntityTypeId()] == 0)
                flag[entity.getEntityTypeId()] = 1;
        }
        int score = 0;
        for (int i :
                flag) {
            if (i != 0)
                score++;
        }
        return score;
    }

    /**
     * 通过余弦相似度法则 计算两个句子的相似度
     *
     * @param sent1 句子对象
     * @param sent2 句子对象
     * @return 余弦值(相似度)
     */
    public static double getSim(Sentence sent1, Sentence sent2) {
        if (sent1.getWordList() == null)
            SegmentHandler.segment(sent1);
        if (sent2.getWordList() == null)
            SegmentHandler.segment(sent2);
        Set<String> wordSet = new HashSet<>();
        for (Word word :
                sent1.getWordList()) {
            wordSet.add(word.getCont());
        }
        for (Word word :
                sent2.getWordList()) {
            wordSet.add(word.getCont());
        }
        int[] a = new int[wordSet.size()];
        int[] b = new int[wordSet.size()];
        int i = 0;
        for (String wordCont :
                wordSet) {
            if (isIncluded(wordCont, sent1.getWordList()))
                a[i] = 1;
            if (isIncluded(wordCont, sent2.getWordList()))
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
     * @param wordCont 词
     * @param wordList 词列表
     * @return 是否包含
     */
    private static boolean isIncluded(String wordCont, List<Word> wordList) {
        for (Word word :
                wordList) {
            if (word.getCont().equals(wordCont))
                return true;
        }
        return false;
    }

    /**
     * 计算标题的可参考价值
     *
     * @return 标题和主题词集合重复的词（动词、名词）的个数
     */
    private double getTitleScore() {
        int titleScore = 0;
        Sentence title = news.getTitleSent();
        List<Word> wordList = title.getWordList();
        for (Word word :
                wordList) {
            if (word.getPos().contains("n") || word.getPos().contains("v")) //只判断动词和名词
                if (topicWordMap.containsKey(word.getCont())) {
                    titleScore++;
                }
        }
        return titleScore;
    }

    /**
     * 统计句子中触发词的个数，如果大于
     *
     * @param sentence 句子
     * @return 匹配的个数
     */
    @SuppressWarnings("unused")
	private Set<String> getTriggerWordScores(Sentence sentence) {
        Set<String> matchedTriggerWordSet = new HashSet<>();
        List<Word> wordList = sentence.getWordList();
        for (Word word :
                wordList) {
            //如果这个词同时是触发词和关键词
            if (triggerWordMap.containsKey(word.getCont()) || topicWordMap.containsKey(word.getCont())) {
                matchedTriggerWordSet.add(word.getCont());//将匹配到的触发词保存下来。
            }
        }
        return matchedTriggerWordSet;
    }

    /**
     * 计算句子中所有词的tf-idf累加和
     *
     * @param sentence 句子
     * @return tf-idf累加和
     */
    private double getAllSentTfidf(Sentence sentence) {
        List<Word> wordList = sentence.getWordList();
        int length = 1;
        int value = 0;
        for (Word word :
                wordList) {
            if (tfidfMap.containsKey(word.getCont())) {
                value += tfidfMap.get(word.getCont());
            }
            length++;
        }
        return sigmoid(value / length);
    }

    /**
     * 生成当前文章的主题词集合 并保存所有词的tf-idf值
     */
    private void buildTopicWordSet() {
        List<Sentence> sentenceList = this.news.getSentenceList();
        List<Word> wordList;
        //sentenceList.add(news.getTitleSent()); //标题也要一起处理
        for (Sentence sentObj :
                sentenceList) {
            wordList = sentObj.getWordList();
            for (Word word :
                    wordList) {
                //不处理停用词和标点
                if (word.getPos().equals("u") || word.getPos().equals("wp"))
                    continue;
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
            if (dfMap.get(word) == null) {
                Map<String, Integer> wordMap = new HashMap<>();
                wordMap.put(word, df);
                dfMap.put(word, wordMap);
                commonMapper.insertDFOne(word, df);
            } else {
                df = Integer.parseInt(String.valueOf(dfMap.get(word).get("df_value"))) + 1;
                dfMap.get(word).put(word, df);
                commonMapper.updateDfOne(word, df);
            }
            int tf = entry.getValue();
            int tfIdf = tfIdf(tf, df, size);
            if (tfIdf > MIN_TF_IDF)
                topicWordMap.put(word, tfIdf);
            tfidfMap.put(word, tfIdf);
        }
    }

    /**
     * sigmoid函数 归一化
     *
     * @param num 待归一化数
     * @return 归一化结果
     */
    private static double sigmoid(double num) {
        return 1 / (1 + Math.pow(Math.E, -num));
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

    /**
     * 打印结果
     *
     * @param sentenceTreeMap 抽取的主题句
     */
    @SuppressWarnings("unused")
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
            System.out.println("\n**事件要素：**");
            EventElementsExtractor eee = new EventElementsExtractor(entry.getValue());
            eee.eeehandler();
            System.out.println("\n**关键触发词：**");
            System.out.println();
        }
        System.out.println("---");
    }
}
