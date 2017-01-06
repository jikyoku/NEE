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
            System.exit(0);
        }
        PreProcess.segment(newsMap); //分词
        Integer lastId = newsMap.lastKey();//得到最后一条新闻的id
        commonMapper.insertNewLog(startId, lastId); //写入处理log

        //主题句抽取
        for (Map.Entry<Integer, News> newsEntry :
                newsMap.entrySet()) {
            News newsObj = newsEntry.getValue();
            System.out.println("主题句抽取：");
            newsHandler(newsObj);
        }
        System.out.println("匹配度：" + (double) matchedSize / newsMap.size() * 100 + "%");
        System.out.println("matched size:" + matchedSize);
    }


    /**
     * 批处理每一篇新闻
     *
     * @param newsObj 新闻对象
     * @return 新闻主题句
     */
    private static TreeMap<Double, Sentence> newsHandler(News newsObj) {
        TopicSentExtractor tse = new TopicSentExtractor(newsObj);
        TreeMap<Double, Sentence> topicSentMap = tse.getTopicSents(); //抽取主题句
        String originCoreSent = commonMapper.getCoreSentByTitle(newsObj.getTitle());
        if (isMatch(originCoreSent, topicSentMap)) {
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
        news.setTitle("内部信59天后跃亭·贾发布FF 91超级汽车 乐视二股东：要马上投资10亿给FF");
        news.setContent("2016年1月4日，CES会场，FF 91与4个竞争对手，展开时速对比并获胜，对手有宾利Bentayga、法拉利488GTB及特斯拉的Modle X和Modle S。FF 91是Faraday Future （简称FF）发布的首款电动车。大屏幕显示，FF 91从0加速到96km/h仅需2.39秒。\n" +
                "　　贾跃亭出现在CES发布会的后半部分，他满脸含笑、一脸憨厚地从FF 91下车。主持人Nick Sampson（他还是FF的研发与工程高级副总裁）让他按一按FF 91，使其实现自动泊车。不过，在他按了之后，这辆车并未动起来，主持人开玩笑说：“今晚的FF 91有点懒。”好在贾跃亭演讲结束后，会场灯光骤降，FF 91最终成功自动泊车。");
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
