package edu.bupt.main.ced;

import edu.bupt.http.HttpRequest;
import edu.bupt.model.Entity;
import edu.bupt.model.Sentence;

import edu.bupt.model.Word;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shi xu on 2016-08-05.
 */

public class EntityExtractor {
    public static List<Entity> entityExtract(Sentence sentence){
        List<Word> wordList  = sentence.getWordList();
        List<Entity> entityList = new ArrayList<>();
        String regex = "nt|ns|ni|nl|nh";
        Pattern pattern = Pattern.compile(regex);
        for (Word word:wordList) {
            if (pattern.matcher(word.getPos()).find()) {
                Entity entity = new Entity();
                entity.setPosition(word.getId());
                entity.setSentenceId(sentence.getId());
                entity.setEntityContent(word.getCont() + "-" + word.getPos());
                switch (word.getPos()) {
                    case "nt":           //时间实体
                        entity.setEntityTypeId(Entity.TIME_ENTITY);
                        entityList.add(entity);
                        break;
                    case "ns":           //地点实体
                    case "nl":
                        entity.setEntityTypeId(Entity.LOC_ENTITY);
                        entityList.add(entity);
                        break;
                    case "nh":          //人物实体
                        entity.setEntityTypeId(Entity.PER_ENTITY);
                        entityList.add(entity);
                        break;
                    case "ni":          //组织机构实体
                        entity.setEntityTypeId(Entity.ORG_ENTITY);
                        entityList.add(entity);
                        break;
                }
            }
        }
        sentence.setEntityList(entityList);
        return entityList;
    }
    public static List<Entity> old_entityExtract(Sentence sentence) {
        String[] words = sentence.getWords();
        String[] taggers = sentence.getTaggers();

        String regex = "t|ns|nr|nt";
        Pattern pattern = Pattern.compile(regex);
        List<Entity> entityList = new ArrayList<>();
        for (int j = 0; j < taggers.length; j++) {
            if (pattern.matcher(taggers[j]).find()) {
                Entity entity = new Entity();
                entity.setPosition(j);
                entity.setSentenceId(sentence.getId());
                entity.setEntityContent(words[j] + "-" + taggers[j]);
                switch (taggers[j]) {
                    case "t":           //时间实体
                        entity.setEntityTypeId(Entity.TIME_ENTITY);
                        entityList.add(entity);
                        break;
                    case "ns":           //地点实体
                        entity.setEntityTypeId(Entity.LOC_ENTITY);
                        entityList.add(entity);
                        break;
                    case "nr":          //人物实体
                        entity.setEntityTypeId(Entity.PER_ENTITY);
                        entityList.add(entity);
                        break;
                    case "nt":          //组织机构实体
                        entity.setEntityTypeId(Entity.ORG_ENTITY);
                        entityList.add(entity);
                        break;
                }
            }
        }
        return entityList;
    }
}
