package edu.bupt.ltp4j;


import java.util.ArrayList;
import java.util.List;
import edu.hit.ir.ltp4j.*;

public class LTP4J {
	
	static{
		Segmentor.create("model\\cws.model");
		Postagger.create("model\\pos.model");
		NER.create("model\\ner.model");
		Parser.create("model\\parser.model");
		SRL.create("model\\srl");
	}
	
	public static void main(String[] args) {
		String sent = "　　教育部将积极推动教学质量评价监督，要求高校建立健全教学质量自评体系，开展对高校教学质量的审核评估;对高水平大学的工程、医学、金融、会计等职业性强的专业，开展专业认证，确立国际标准;支持第三方社会机构开展多种形式的学校教育教学评价";
		List<String> words = segmentor(sent);
		System.out.println("分詞：" + words);
		List<String> tags = postagger(words);
		System.out.println("詞性標注：" + tags);

		List<String> ners = ner(words, tags);
		System.out.println("實體抽取：" + ners);

		List<Integer> heads = new ArrayList<>();
		List<String> deprels = new ArrayList<>();
		
		parse(words, tags, heads, deprels);
		heads = modify(heads);
		System.out.println(heads);
		System.out.println(deprels);

		System.out.println("------------------");
		
		
		 words = segmentor(sent);
		System.out.println("分詞：" + words);
		 tags = postagger(words);
		System.out.println("詞性標注：" + tags);

		 ners = ner(words, tags);
		System.out.println("實體抽取：" + ners);

		 heads = new ArrayList<>();
		 deprels = new ArrayList<>();
		
		parse(words, tags, heads, deprels);
		heads = modify(heads);
		System.out.println(heads);
		System.out.println(deprels);

		System.out.println("------------------");
		
		srl(words, tags, ners, heads, deprels);
		/*
		 * List<String> postaggers = postagger(words); List<String> ners =
		 * ner(words, postaggers); parse(words, postaggers, new
		 * ArrayList<Integer>(), new ArrayList<String>());
		 * System.out.println(words); System.out.println(postaggers);
		 * System.out.println(ners);
		 */
		// testParse();
		
		//testSrl();
	}
	
	public static List<Integer> modify(List<Integer> heads){
		for (int i = 0; i < heads.size(); i++) {
			heads.set(i, heads.get(i) - 1);
		}
		return heads;
	}

	public static List<String> segmentor(String sent) {
		List<String> words = new ArrayList<String>();
		Segmentor.segment(sent, words);
		//Segmentor.release();
		return words;
	}

	public static List<String> postagger(List<String> words) {
		List<String> postags = new ArrayList<String>();
		Postagger.postag(words, postags);
		return postags;
	}

	public static List<String> ner(List<String> words, List<String> tags) {
		List<String> ners = new ArrayList<String>();
		NER.recognize(words, tags, ners);
		return ners;
	}

	public static void parse(List<String> words, List<String> tags, List<Integer> heads, List<String> deprels) {

		Parser.parse(words, tags, heads, deprels);
//		for (int i = 0; i < heads.size(); i++) {
//			System.out.println(heads.get(i) + "," + deprels.get(i));
//		}
		//Parser.release();
	}

	public static List<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>> srl(List<String> words, List<String> tags,
						   List<String> ners, List<Integer> heads,
						   List<String> deprels) {

		List<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>> srls = new ArrayList<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>>();
		SRL.srl(words, tags, ners, heads, deprels, srls);
//		for (int i = 0; i < srls.size(); ++i) {
//			System.out.println(srls.get(i).first + ":");
//			for (int j = 0; j < srls.get(i).second.size(); ++j) {
////				System.out.println("   tpye = " + srls.get(i).second.get(j).first + " beg = "
////						+ srls.get(i).second.get(j).second.first + " end = " + srls.get(i).second.get(j).second.second);
//			}
//		}
		//SRL.release();
		return srls;
	}

	public static void testParse() {
		if (Parser.create("model\\parser.model") < 0) {
			System.err.println("load failed");
			return;
		}
		List<String> words = new ArrayList<String>();
		List<String> tags = new ArrayList<String>();
		words.add("一把手");
		tags.add("n");
		words.add("亲自");
		tags.add("d");
		words.add("过问");
		tags.add("v");
		words.add("。");
		tags.add("wp");
		List<Integer> heads = new ArrayList<Integer>();
		List<String> deprels = new ArrayList<String>();
		int size = Parser.parse(words, tags, heads, deprels);
		for (int i = 0; i < size; i++) {
			System.out.print(heads.get(i) + ":" + deprels.get(i));
			if (i == size - 1) {
				System.out.println();
			} else {
				System.out.print("        ");
			}
		}
		Parser.release();
	}

	public static void testSrl() {
		
		ArrayList<String> words = new ArrayList<String>();
		words.add("一把手");
		words.add("亲自");
		words.add("过问");
		words.add("。");
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("n");
		tags.add("d");
		tags.add("v");
		tags.add("wp");
		ArrayList<String> ners = new ArrayList<String>();
		ners.add("O");
		ners.add("O");
		ners.add("O");
		ners.add("O");
		ArrayList<Integer> heads = new ArrayList<Integer>();
		heads.add(2);
		heads.add(2);
		heads.add(-1);
		heads.add(2);
		ArrayList<String> deprels = new ArrayList<String>();
		deprels.add("SBV");
		deprels.add("ADV");
		deprels.add("HED");
		deprels.add("WP");
		List<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>> srls = new ArrayList<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>>();
		SRL.srl(words, tags, ners, heads, deprels, srls);
		for (int i = 0; i < srls.size(); ++i) {
			System.out.println(srls.get(i).first + ":");
			for (int j = 0; j < srls.get(i).second.size(); ++j) {
				System.out.println("   tpye = " + srls.get(i).second.get(j).first + " beg = "
						+ srls.get(i).second.get(j).second.first + " end = " + srls.get(i).second.get(j).second.second);
			}
		}
		SRL.release();

	}
}