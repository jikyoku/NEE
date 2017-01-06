package edu.bupt.model;

import java.util.List;

/**
 * Created by shixu on 2016/11/30.
 */


public class Word {

    int id;   //位置
    String cont; //内容
    String pos; //词性
    String ne; //命名实体类型
    List<Argument> arguments;   //语义角色标注
    Integer parent;
    String relate;

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", cont='" + cont + '\'' +
                ", pos='" + pos + '\'' +
                ", ne='" + ne + '\'' +
                ", parent='" + parent + '\'' +
                ", relate='" + relate + '\'' +
                ", arguments=" + arguments +
                '}';
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getRelate() {
        return relate;
    }

    public void setRelate(String relate) {
        this.relate = relate;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getNe() {
        return ne;
    }

    public void setNe(String ne) {
        this.ne = ne;
    }
}
