package edu.bupt.main.segment;

import edu.bupt.ltp4j.LTP4J;
import edu.bupt.main.ced.EntityExtractor;
import edu.bupt.model.Argument;
import edu.bupt.model.Sentence;
import edu.hit.ir.ltp4j.Pair;
import java.util.ArrayList;
import java.util.List;
import edu.bupt.model.Word;

/**
 * Created by shixu on 2016-07-25.
 */
public class SegmentHandler {
	public static void main(String[] args) {
		Sentence sentence = new Sentence();
		sentence.setSentenceContent("上海交大发布的《2016年就业质量报告》显示，截至2016年10月31日，在就业的毕业生中");
		segment(sentence);
		EntityExtractor.entityExtract(sentence);
		System.out.println(sentence.getEntityList());
	}

	/**
	 * 分词、词性标注、句法分析、语义角色分析
	 * 
	 * @param sentence
	 * @return
	 */
	public static Sentence segment(Sentence sentence) {
		List<String> words = LTP4J.segmentor(sentence.getSentenceContent());
		//System.out.println(words);
		List<String> tags = LTP4J.postagger(words);
		//System.out.println(tags);
		List<String> ners = LTP4J.ner(words, tags);
		sentence.setNers(ners);
		//System.out.println(ners);
		List<Integer> heads = new ArrayList<Integer>();
		List<String> deprels = new ArrayList<>();
		LTP4J.parse(words, tags, heads, deprels);

		heads = LTP4J.modify(heads);

		List<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>> srls = LTP4J.srl(words, tags, ners, heads,
				deprels);

		List<Word> wordList = new ArrayList<>();
		for (int i = 0; i < words.size(); i++) {
			Word word = new Word();
			word.setId(i);
			word.setCont(words.get(i));
			word.setPos(tags.get(i));
			word.setNe(ners.get(i));
			word.setParent(heads.get(i));
			word.setRelate(deprels.get(i));
			wordList.add(word);
		}
		for (int i = 0; i < srls.size(); ++i) {
			Word word = wordList.get(srls.get(i).first);
			List<Argument> arguments = new ArrayList<>();
			for (int j = 0; j < srls.get(i).second.size(); ++j) {
				Argument argument = new Argument();
				argument.setId(j);
				argument.setBeg(srls.get(i).second.get(j).second.first);
				argument.setEnd(srls.get(i).second.get(j).second.second);
				argument.setType(srls.get(i).second.get(j).first);
				arguments.add(argument);
			}
			word.setArguments(arguments);
		}
		
		sentence.setWordList(wordList);
	//	entityExtract(sentence, ners);		
		return sentence;
	}

	
}
