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
    private static Map<Integer, News> newsMap;
    private static CommonMapper commonMapper = new CommonMapperImpl();
    private static int mathchedSize = 0;

    public static void main(String[] args) throws IOException {
        int startId = 5001;
        newsMap = new TreeMap<>(newsService.getNewsMap(startId, null, null));
        System.out.println("=====news size=====：" + newsMap.size());
        for (Map.Entry<Integer, News> newsEntry :
                newsMap.entrySet()) {
            News newsObj = newsEntry.getValue();
            handler(newsObj);
        }
        System.out.println("匹配度：" + (double) mathchedSize / newsMap.size() * 100 + "%");
        System.out.println("matchedsize:" + mathchedSize);
    }

    private static void handler(News newsObj) {
        newsObj.setContent(PreProcess.clean(newsObj.getContent()));
        List<Sentence> sentenceList = PreProcess.newsSeparate(newsObj.getContent(), PreProcess.END_LABEL);
        for (Sentence sentObj :
                sentenceList) {
            //SegmentHandler.ansjPos(sentObj);
            SegmentHandler.xfYunPos(sentObj);
        }
        newsObj.setSentenceList(sentenceList);
        TopicSentExtractor tse = new TopicSentExtractor(newsObj);
        TreeMap<Double, Sentence> sentenceMap = tse.handler();
        String coreSent = commonMapper.getCoreSentByTitle(newsObj.getTitle());
        if (isMatch(coreSent, sentenceMap)) {
            mathchedSize++;
        }
    }

    public static boolean isMatch(String coreSent, TreeMap<Double, Sentence> sentenceTreeMap) {
        Map.Entry<Double, Sentence> entry = sentenceTreeMap.firstEntry();
        Sentence candidateSentObj = entry.getValue();
        Sentence coreSentObj = new Sentence(coreSent);
        double sim = TopicSentExtractor.getSim(coreSentObj, candidateSentObj);
        if (sim > 0.5) {
            return true;
        }
        return false;
    }


    @Test
    public void test() {
        News news = new News();
        news.setTitle("明年近800万毕业生求职 媒体：就业难实为择业难");
        news.setContent("据人社部最新消息，今年11月份，城镇新增就业人数81万。今年1月至11月，城镇新增就业人数1249万人，完成全年目标任务的124.9%。但是随着高校毕业生总量的持续增加， 2016年中国高校毕业生规模将接近800万，较去年增长16万。虽然就业焦虑依然存在，但大学生的择业观渐渐趋于理性务实、就业渠道日益多元化，也是不争的事实。\n" +
                "\n" +
                "　　透过“就业难”的表象，我们要看到“择业难”的实质。其实，从某个角度来说，并不一定是工作难找，也不是岗位难求，只是心里想着挣高薪水，骨子里却没真本事。自己想做的挣钱少，工资不错的还嫌没有户口和编制。鱼和熊掌不能兼得，工作能力还配不上“野心”，这样下去，工作难找是不足为奇的。\n" +
                "\n" +
                "　　职业理想与社会报偿不相符是造成求职“选择困难症”的一大因素。去追求所谓的理想，还是谋求高薪或安逸，是毕业生求职面临的最现实的问题。一些知名院校的高材生，他们的苦恼是不知是要坚守学了七年的专业理想还是去一家有编制有户口的国企。对初心念念不忘，却对报酬耿耿于怀，着实让人难以抉择。\n" +
                "\n" +
                "　　同时，社会的价值观念导向也是造成毕业生求职困境的一大影响因素。按照传统观念，国企、事业单位、公务员似乎是“金饭碗”——工作稳定、压力小、社会地位高，依然是许多家长对于自己孩子工作的期待。然而，社会在不断进步，当年被骂“丢母校脸”的北大毕业生卖猪肉的生意做得风声水起，休学创业、灵活就业等多元化的选择也逐渐被更多人接纳和认可。\n" +
                "\n" +
                "　　破解就业之难，并不是一味要求求职者“自降身价”，每一个大学生对自己要有清醒的认识和明确的规划。如果等到大四、研三才想起做职业规划，那也只能坐等毕业“喝西北风”。不少大学生入学第一年就努力学习专业知识，夯实基础，第二年便争取到了这家单位的实习机会还表现得十分优秀，最后他在校园招聘中脱颖而出。\n" +
                "\n" +
                "　　如今，随着“铁饭碗”和“洋饭碗”吸引力下降，正风反腐的凌厉声势，大大压减体制内的“灰色福利”；而随着“中国智造”的崛起和转型升级的加快，“洋饭碗”不如从前吃香。哪儿有梧桐树哪儿就有金凤凰，大学毕业生更多流向民企和中小微企业，正是社会活力的体现。\n" +
                "\n" +
                "　　选择越是多元，越需要明确的自我认知。无论是自主择业、灵活就业，还是休学创业、自谋出路，或者出国升学、继续深造，职业选择没有对错，但每一位求职者都要对自己的选择负责，对自己的岗位尽责，抓住“出彩”的机会，去赢得应有的尊重。");
        handler(news);
    }
}
