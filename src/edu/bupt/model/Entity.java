package edu.bupt.model;

/**
 * Created by shixu on 2016-07-28.
 * 实体抽取
 * 时间实体
 * 命名实体
 */

public class Entity {

    /**
     *  四种实体类型：人名、地名、组织机构、时间
     *  */
    public static final int TIME_ENTITY = 1;
    public static final int PER_ENTITY = 2;
    public static final int LOC_ENTITY = 3;
    public static final int ORG_ENTITY = 4;

    public Entity(String s) {
        this.entityContent = s;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", sentenceId=" + sentenceId +
                ", entityTypeId=" + entityTypeId +
                ", entityContent='" + entityContent + '\'' +
                ", position=" + position +
                '}';
    }

    public Entity(String cont,int type){
        this.entityContent = cont;
        this.entityTypeId = type;
    }

    private int id;
    private int sentenceId;
    private int entityTypeId;
    private String entityContent;
    private int position;

    public int getId() {
        return id;
    }

    public int getEntityTypeId() {
        return entityTypeId;
    }

    public String getEntityContent() {
        return entityContent;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSentenceId(int sentenceId) {
        this.sentenceId = sentenceId;
    }

    public void setEntityTypeId(int entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public void setEntityContent(String entityContent) {
        this.entityContent = entityContent;
    }
}
