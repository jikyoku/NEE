package edu.bupt.main;

import edu.bupt.main.ced.EventElementsExtractor;
import edu.bupt.main.ced.TopicSentExtractor;
import edu.bupt.main.preprocess.PreProcess;
import edu.bupt.mapper.CommonMapper;
import edu.bupt.mapperimpl.CommonMapperImpl;
import edu.bupt.model.Event;
import edu.bupt.model.News;
import edu.bupt.model.Sentence;
import edu.bupt.service.NewsService;
import edu.bupt.serviceimpl.NewsServiceImpl;
import edu.bupt.util.json.JsonParser;

import org.json.JSONArray;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by shixu on 2016-07-25.
 *
 * @author shixu
 */
public class MainHandler {

	private static NewsService newsService = new NewsServiceImpl();
	private static CommonMapper commonMapper = new CommonMapperImpl();

	public static void main(String[] args) throws IOException {
		//cmd(); // 命令行执行

		testCase(); // 执行一个测试用例
	}

	@Test
	public static void testCase() {
		News news = new News();
		news.setTitle("习近平同塞尔维亚总统尼科利奇会谈");
		news.setContent(
				"3月30日，国家主席习近平在北京人民大会堂同塞尔维亚总统尼科利奇举行会谈。" + "这是会谈前，习近平在人民大会堂东门外广场为尼科利奇举行欢迎仪式。" + "新华社记者 庞兴雷摄本报北京3月30日电  "
						+ "（记者杨晔）国家主席习近平30日在人民大会堂同塞尔维亚总统尼科利奇举行会谈。" + "两国元首一致同意，推动中塞全面战略伙伴关系不断取得新成果，更好造福两国人民。");
		JSONArray jsonArray = process(news);
		System.out.println("新闻标题：" + news.getTitle());
		System.out.println("新闻正文：" + news.getContent());
		System.out.println("识别结果：");
		System.out.println(jsonArray);
	}

	/**
	 * 命令行执行操作
	 */
	public static void cmd() {
		String menu = "--------欢迎使用--------\n" + "1、输入新闻\n" + "2、退出\n" + "----------------------\n";
		String choice = null;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println(menu);
			choice = scanner.next();
			if ("1".equals(choice)) {
				System.out.println("请输入新闻标题：");
				String newsTitle = scanner.next();
				System.out.println("请输入新闻正文：");
				String newsContent = scanner.next();
				News news = new News();
				news.setTitle(newsTitle);
				news.setContent(newsContent);
				JSONArray res = process(news);
				System.out.println("结果：" + res);
				System.exit(0);
			} else if ("2".equals(choice)) {
				System.out.println("谢谢使用");
				System.exit(0);
			} else {
				System.err.println("请输入正确选项（1 或 2）");
			}
		}
	}

	/**
	 * 从数据库中读取新闻进行处理
	 */
	@SuppressWarnings("unused")
	private static void demonProcess() {
		Integer startId = commonMapper.getLogEndId() + 1; // 数据库中未处理新闻的起始ID
		Integer endId = null; // 未处理新闻的结束ID
		Integer length = null; // 需要处理的新闻数量
		// 获取未处理的新闻
		TreeMap<Integer, News> newsMap = new TreeMap<>(newsService.getNewsMap(0, endId, 100));
		if (newsMap.size() == 0) {
			System.err.println("没有待处理新闻！");
		} else {
			batchProcess(newsMap); // 批处理新闻
			Integer lastId = newsMap.lastKey();// 得到最后一条新闻的id
			commonMapper.insertNewLog(startId, lastId); // 写入处理记录
			commonMapper.updateSize(TopicSentExtractor.size + newsMap.size()); // 更新处理过的新闻数目
		}
	}

	/**
	 * 批处理新闻
	 * 
	 * @param newsMap
	 *            新闻集合，键为id， 值为新闻对象
	 */
	public static void batchProcess(TreeMap<Integer, News> newsMap) {
		for (Map.Entry<Integer, News> newsEntry : newsMap.entrySet()) {
			News newsObj = newsEntry.getValue();
			process(newsObj);
		}
	}

	/**
	 * 处理一篇新闻
	 * 
	 * @param news
	 * @return JSONArray对象
	 */
	public static JSONArray process(News news) {
		PreProcess.segment(news); // 分词
		TreeMap<Double, Sentence> keySentenceMap = getKeySentence(news); // 得到新闻关键句
		List<Event> events = new ArrayList<>();
		for (Map.Entry<Double, Sentence> keySentenceEntry : keySentenceMap.entrySet()) {
			Sentence sent = keySentenceEntry.getValue();
			getEvent(sent); // 对关键句进行事件抽取
			events.addAll(sent.getEventList());
		}

		JSONArray jsonArray = JsonParser.buildJSONArray(events);
		return jsonArray;
	}

	/**
	 * 批处理每一篇新闻 得到关键事件句
	 *
	 * @param newsObj
	 *            新闻对象
	 * @return 新闻主题句集合
	 */
	private static TreeMap<Double, Sentence> getKeySentence(News newsObj) {
		TopicSentExtractor tse = new TopicSentExtractor(newsObj);
		TreeMap<Double, Sentence> topicSentMap = tse.getTopicSentences(); // 抽取主题句
		return topicSentMap;
	}

	/**
	 * 对句子进行事件抽取
	 * 
	 * @param sentence
	 *            句子
	 */
	private static void getEvent(Sentence sentence) {
		EventElementsExtractor eee = new EventElementsExtractor(sentence);
		eee.eeehandler();
	}

	/**
	 * 判断预测的主题句集合和人工标注的主题句是否一致
	 *
	 * @param coreSent
	 *            句子内容
	 * @param sentenceTreeMap
	 *            主题句集合
	 * @return 是否匹配 相似度大于0.5为匹配
	 */
	@SuppressWarnings("unused")
	private static boolean isMatch(String coreSent, TreeMap<Double, Sentence> sentenceTreeMap) {
		Sentence realCoreSentObj = new Sentence(coreSent);
		for (Map.Entry<Double, Sentence> entry : sentenceTreeMap.entrySet()) {
			double sim = TopicSentExtractor.getSim(entry.getValue(), realCoreSentObj);
			if (sim > 0.5) {
				return true;
			}
		}
		return false;
	}

}
