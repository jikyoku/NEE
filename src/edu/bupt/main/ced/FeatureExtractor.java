package edu.bupt.main.ced;

import edu.bupt.model.*;
import java.util.*;

/**
 * Created by shi xu on 2016-07-28.
 * 实体抽取
 * 候选事件抽取
 * 二元分类特征向量构建
 *
 * @author shixu
 */
public class FeatureExtractor {
    /**
     *
     * @param sentence
     * 句子
     * @param entityList
     * 实体列表
     * @param i
     * 触发词在句子中的位子
     * @return
     * 返回特征向量
     */
    public static Feature featureExtract(Sentence sentence, List<Entity> entityList, int i) {
        String[] words = sentence.getWords();  //词
        String[] taggers = sentence.getTaggers(); //标注
        Feature feature = new Feature();

        feature.setTriggerWord(words[i]);
        feature.setTriggerWordPos(taggers[i]);
        feature.setSentence(sentence.getSentenceContent());
        for (int j = 1; j < 5; j++) {
            if (i - j >= 0)
                feature.setPOS(j, Feature.BEFORE, taggers[i - j]);
            if (i + j < words.length)
                feature.setPOS(j, Feature.AFTER, taggers[i + j]);
        }

        if (entityList != null && entityList.size() > 0) {   //记录触发词左右一个实体
            int befOneEnPos = entityList.get(0).getPosition();
            int aftOnePos = entityList.get(entityList.size() - 1).getPosition();
            String befOneEnTy = "##";  //表示before1EntityType
            String aftOneEnTy = "##";  //表示after1EntityType
            if (befOneEnPos < i && aftOnePos > i) {
                for (Entity e : entityList) {
                    if (e.getPosition() < i)
                        befOneEnTy = String.valueOf(e.getEntityTypeId());
                    if (e.getPosition() > i) {
                        aftOneEnTy = String.valueOf(e.getEntityTypeId());
                        break;
                    }
                }
            } else if (aftOnePos < i) {
                for (Entity e : entityList) {
                    if (e.getPosition() < i)
                        befOneEnTy = String.valueOf(e.getEntityTypeId());
                    else
                        break;
                }
            } else if (befOneEnPos > i) {
                for (Entity e : entityList) {
                    if (e.getPosition() > i) {
                        aftOneEnTy = String.valueOf(e.getEntityTypeId());
                        break;
                    }
                }
            }
            feature.setBefore1EntityType(befOneEnTy);
            feature.setAfter1EntityType(aftOneEnTy);
        }
        return feature;
    }
//    /**
//     * 最大熵分类 判断该特征向量是否是真的候选事件
//     * @param feature
//     * 特征对象
//     * @return
//     * 候选事件的真和假
//     */
//    public static boolean isRealEvent(Feature feature) {
//        MaxEnt maxEnt = MaxEnt.getMaxEntInstance();
//        List<String> fieldList = new ArrayList<String>();
//        fieldList.add(feature.getTriggerWord());
//        fieldList.add(feature.getTriggerWordPos());
//        fieldList.add(feature.getBefore4pos());
//        fieldList.add(feature.getBefore3pos());
//        fieldList.add(feature.getBefore2pos());
//        fieldList.add(feature.getBefore1pos());
//        fieldList.add(feature.getAfter1pos());
//        fieldList.add(feature.getAfter2pos());
//        fieldList.add(feature.getAfter3pos());
//        fieldList.add(feature.getAfter4pos());
//        fieldList.add(feature.getBefore1EntityType());
//        fieldList.add(feature.getAfter1EntityType());
//
//        Pair<String, Double>[] result = maxEnt.predict(fieldList);  // 预测各个类标识的概率各是多少
//        if (result[0].getValue() > result[1].getValue())
//            return Boolean.valueOf(result[0].getKey());
//        else
//            return Boolean.valueOf(result[1].getKey());
//    }
}
