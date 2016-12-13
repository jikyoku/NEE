package edu.bupt.model;

/**
 * Created by shixu on 2016/11/30.
 */


public class Word {

    int id;   //位置
    String cont; //内容
    String pos; //词性
    String ne; //命名实体类型

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", cont='" + cont + '\'' +
                ", pos='" + pos + '\'' +
                ", ne='" + ne + '\'' +
                '}';
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
