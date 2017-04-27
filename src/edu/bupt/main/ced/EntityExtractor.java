package edu.bupt.main.ced;

import edu.bupt.main.segment.SegmentHandler;
import edu.bupt.model.Entity;
import edu.bupt.model.Sentence;
import edu.bupt.model.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shi xu on 2016-08-05.
 *
 */

public class EntityExtractor {

    private static TimeTagger timeTagger = new TimeTagger();
    
    
    public static void entityExtract(Sentence sentence) {
		List<Entity> entityList = new ArrayList<>();
		List<Word> wordList = sentence.getWordList();
		List<String> ners = sentence.getNers();
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < ners.size(); i++) {
			if (!ners.get(i).equals("O")) {
				Entity entity = new Entity(wordList.get(i).getCont());
				entity.setPosition(i);
				if (ners.get(i).equals("S-Ns")) {
					entity.setEntityTypeId(Entity.LOC_ENTITY);
				} else if (ners.get(i).equals("S-Nh")) {
					entity.setEntityTypeId(Entity.PER_ENTITY);
				} else if (ners.get(i).equals("S-Ni")) {
					entity.setEntityTypeId(Entity.ORG_ENTITY);
				} else if (ners.get(i).equals("B-Ns")) {
					entity.setEntityTypeId(Entity.LOC_ENTITY);
					i++;
					str.append(wordList.get(i).getCont());
					while (!ners.get(i).contains("E-N")) {
						str.append(wordList.get(i).getCont());
						i++;
					}
					str.append(wordList.get(i).getCont());
					entity.setEntityContent(str.toString());
				} else if (ners.get(i).equals("B-Nh")) {
					entity.setEntityTypeId(Entity.PER_ENTITY);
					i++;
					str.append(wordList.get(i).getCont());
					while (!ners.get(i).contains("E-N")) {
						str.append(wordList.get(i).getCont());
						i++;
					}
					str.append(wordList.get(i).getCont());
					entity.setEntityContent(str.toString());
				} else if (ners.get(i).equals("B-Ni")) {
					entity.setEntityTypeId(Entity.ORG_ENTITY);
					str.append(wordList.get(i).getCont());
					i++;
					while (!ners.get(i).contains("E-N")) {
						str.append(wordList.get(i).getCont());
						i++;
					}
					str.append(wordList.get(i).getCont());
					entity.setEntityContent(str.toString());
				}
				entityList.add(entity);
			}			
		}
		Entity timeEntity = timeTagger.timeEntityExtract(sentence);
		if (timeEntity != null){
			entityList.add(timeEntity);
		}
		sentence.setEntityList(entityList);
	}
    public static void main(String[] args) {
        Sentence sentence = new Sentence("国务院，河南省习近平,胡锦涛,北京邮电大学,上海交大发布的《2016年就业质量报告》显示，截至2016年10月31日，在就业的毕业生中");
        SegmentHandler.segment(sentence);
        entityExtract(sentence);
        System.out.println(sentence.getEntityList());

    }
}


class TimeTagger {
    private static List<Pattern> timePattern = new ArrayList<>();
    TimeTagger() {
        String timeExpPath = "model/time.exp";
        File expFile = new File(timeExpPath);
        if (expFile.exists() && expFile.isFile()) {
            String exp;
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(expFile), "utf-8"));
                while ((exp = br.readLine()) != null) {
                    timePattern.add(Pattern.compile(exp));
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.err.println(timeExpPath+" not found!");
        }
    }

    /**
     * 标注句子中的时间实体
     *
     * @param s 句子
     * @return Entity
     */
    Entity timeEntityExtract(Sentence s) {
        String text = s.getSentenceContent();
        Entity entity = null;
        int length;
        for (Pattern pattern : timePattern) {
            Matcher matcher = pattern.matcher(text);
            length = 0;
            while (matcher.find()) {
                String time = matcher.group();
                if (entity != null)
                    length = entity.getEntityContent().length();
                if (time.length() > length) {
                    length = time.length();
                    entity = new Entity(time, Entity.TIME_ENTITY);
                }
            }
        }
        return entity;
    }
}
