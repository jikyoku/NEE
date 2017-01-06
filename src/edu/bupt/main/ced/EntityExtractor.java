package edu.bupt.main.ced;

import edu.bupt.http.HttpRequest;
import edu.bupt.model.Entity;
import edu.bupt.model.Sentence;
import edu.bupt.model.Word;
import org.junit.Test;

import java.io.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shi xu on 2016-08-05.
 *
 */

public class EntityExtractor {

    private static TimeTagger timeTagger = new TimeTagger();

    public static List<Entity> entityExtract(Sentence sentence) {
        List<Word> wordList = sentence.getWordList();
        List<Entity> entityList = new ArrayList<>();
        String regex = "B-Ns|E-Ns|S-Ns|S-Ni|S-Nh|E-Ni";
        Pattern pattern = Pattern.compile(regex);
        for (Word word : wordList) {
            if (word.getNe() != null && pattern.matcher(word.getNe()).find()) {
                Entity entity = new Entity(word.getCont());
                entity.setPosition(word.getId());
                entity.setSentenceId(sentence.getId());
                switch (word.getNe()) {
//                    case "nt":           //时间实体
//                        entity.setEntityTypeId(Entity.TIME_ENTITY);
//                        entityList.add(entity);
//                        break;
                    case "B-Ns":           //地点实体
                    case "E-Ns":
                    case "S-Ns":
                        entity.setEntityTypeId(Entity.LOC_ENTITY);
                        entityList.add(entity);
                        break;
                    case "S-Nh":          //人物实体
                        entity.setEntityTypeId(Entity.PER_ENTITY);
                        entityList.add(entity);
                        break;
                    case "S-Ni":          //组织机构实体
                    case "E-Ni":
                        entity.setEntityTypeId(Entity.ORG_ENTITY);
                        entityList.add(entity);
                        break;
                }
            }
        }
        sentence.setEntityList(entityList);
        timeTagger.timeEntityExtract(sentence); //抽取时间实体
        return entityList;
    }

    static List<Entity> entityExtract2(Sentence sentence) {
        String result = HttpRequest.xfyunHttpApi(sentence.getSentenceContent(),
                HttpRequest.PATTERN_NER, HttpRequest.FORMAT_PLAIN);
        result = result.replaceAll(" ", "");
        String regex = "\\[[\\u4e00-\\u9fa5]{0,}\\](Ni|Ns|Nh)";
        List<Entity> entities = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(result);
        while (matcher.find()) {
            Entity entity = new Entity(matcher.group());
            if (matcher.group().contains("Ni")) {
                entity.setEntityTypeId(Entity.ORG_ENTITY);
            } else if (matcher.group().contains("Ns")) {
                entity.setEntityTypeId(Entity.LOC_ENTITY);
            } else if (matcher.group().contains("Nh")) {
                entity.setEntityTypeId(Entity.PER_ENTITY);
            }
            entities.add(entity);
        }
        Entity timeEntity = timeTagger.timeEntityExtract(sentence);
        if (timeEntity != null)
            entities.add(timeEntity);
        sentence.setEntityList(entities);
        return entities;
    }

    public static void main(String[] args) {
        Sentence sentence = new Sentence("上海交大发布的《2016年就业质量报告》显示，截至2016年10月31日，在就业的毕业生中");
        entityExtract2(sentence);
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
