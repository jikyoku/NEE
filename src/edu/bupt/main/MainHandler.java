package edu.bupt.main;

import edu.bupt.main.ced.TopicSentExtractor;
import edu.bupt.main.preprocess.PreProcess;
import edu.bupt.main.segment.SegmentHandler;
import edu.bupt.mapper.CommonMapper;
import edu.bupt.mapperimpl.CommonMapperImpl;
import edu.bupt.model.News;
import edu.bupt.model.Sentence;
import edu.bupt.service.NewsService;
import edu.bupt.serviceimpl.NewsServiceImpl;
import edu.bupt.util.TFIDFUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by shi xu on 2016-07-25.
 *
 * @author shi xu
 */
public class MainHandler {
    private static NewsService newsService = new NewsServiceImpl();
    private static CommonMapper commonMapper = new CommonMapperImpl();
    private static int matchedSize = 0;

    public static void main(String[] args) throws IOException {
        Integer startId = commonMapper.getLogEndId() + 1;
        TreeMap<Integer, News> newsMap = new TreeMap<>(newsService.getNewsMap(0, null, null));
        if (newsMap.size() == 0) {
            System.out.println("没有待处理新闻！");
        } else {
            PreProcess.segment(newsMap); //分词
            Integer lastId = newsMap.lastKey();//得到最后一条新闻的id
            commonMapper.insertNewLog(startId, lastId); //写入处理记录
            //主题句抽取
            for (Map.Entry<Integer, News> newsEntry :
                    newsMap.entrySet()) {
                System.out.println("主题句抽取：");
                News newsObj = newsEntry.getValue();
                newsHandler(newsObj);
            }
            commonMapper.updateSize(TopicSentExtractor.size); //更新处理记录
            System.out.println("匹配度：" + (double) matchedSize / newsMap.size() * 100 + "%");
            System.out.println("matched size:" + matchedSize);
        }
    }


    /**
     * 批处理每一篇新闻
     * @param newsObj 新闻对象
     * @return 新闻主题句集合
     */
    private static TreeMap<Double, Sentence> newsHandler(News newsObj) {
        TopicSentExtractor tse = new TopicSentExtractor(newsObj);
        TreeMap<Double, Sentence> topicSentMap = tse.getTopicSents(); //抽取主题句
        String realCoreSent = commonMapper.getCoreSentByTitle(newsObj.getTitle());
        if (isMatch(realCoreSent, topicSentMap)) {
            matchedSize++;
        }
        return topicSentMap;
    }



    /**
     * 判断预测的主题句集合是否和真的主题句一致
     *
     * @param coreSent        句子内容
     * @param sentenceTreeMap 主题句集合
     * @return 是否匹配
     */
    private static boolean isMatch(String coreSent, TreeMap<Double, Sentence> sentenceTreeMap) {
        Sentence realCoreSentObj = new Sentence(coreSent);
        for (Map.Entry<Double, Sentence> entry :
                sentenceTreeMap.entrySet()) {
            double sim = TopicSentExtractor.getSim(entry.getValue(), realCoreSentObj);
            if (sim > 0.5) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void test() {
        News news = new News();
        news.setTitle("天津大学发布就业质量报告 超四成毕业生选择留津");
        news.setContent("人民网北京1月4日电（孙竞）天津大学近日发布的《2016届毕业生就业质量年度报告》中显示，截至12月30日，学校2016届毕业生总体就业率为 97.70%。在以各种形式直接就业的毕业生中，去往国家重点地区、重点行业、重点单位的毕业生人数占59.20%。天津大学2016届毕业生总人数为8003人。其中本科生3855人，硕士研究生3346人，博士研究生517人。在不同层次毕业生中，硕士毕业生就业率最高，达到98.92%。本科生毕业生、博士毕业生的就业率分别为97.20%、97.68%。根据本科毕业生各专业就业率来看，天津大学的优势学科土木工程、建筑学、城市规划、光电子技术科学、通信工程等专业就业率达到100%。建筑学院的整体就业率达到100%。天津大学2016届毕业生总人数为8003人。其中本科生3855人，硕士研究生3346人，博士研究生517人。在不同层次毕业生中，硕士毕业生就业率最高，达到98.92%。本科生毕业生、博士毕业生的就业率分别为97.20%、97.68%。根据本科毕业生各专业就业率来看，天津大学的优势学科土木工程、建筑学、城市规划、光电子技术科学、通信工程等专业就业率达到100%。建筑学院的整体就业率达到100%。");
        PreProcess.segment(news);
        TopicSentExtractor tse = new TopicSentExtractor(news);
        TreeMap<Double, Sentence> coreSentences = tse.getTopicSents();
        Map<String, Integer> topicWordMap = tse.getTopicWordMap();
        System.out.println(coreSentences);
        System.out.println(topicWordMap);
        System.out.println(coreSentences.firstEntry().getValue().getEvent());
        System.out.println(coreSentences.firstEntry().getValue().getEntityList());
    }
}
