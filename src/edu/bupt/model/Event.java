package edu.bupt.model;

/**
 * Created by shixu on 2017/1/4.
 */
public class Event {
    private String LOC; //位置
    private String A1; //受体
    private String A0; //主体
    private String TMP; //时间
    private Word HED; //核心词
    private Word action; //动词

    @Override
    public String toString() {
        return "Event{" +
                "LOC='" + LOC + '\'' +
                ", A1='" + A1 + '\'' +
                ", A0='" + A0 + '\'' +
                ", TMP='" + TMP + '\'' +
                ", HED=" + HED +
                ", action=" + action +
                '}';
    }

    public Word getHED() {
        return HED;
    }

    public void setHED(Word HED) {
        this.HED = HED;
    }

    public Word getAction() {
        return action;
    }

    public void setAction(Word action) {
        this.action = action;
    }

    public String getA1() {
        return A1;
    }

    public void setA1(String a1) {
        A1 = a1;
    }

    public String getA0() {
        return A0;
    }

    public void setA0(String a0) {
        A0 = a0;
    }

    public String getTMP() {
        return TMP;
    }

    public void setTMP(String TMP) {
        this.TMP = TMP;
    }

    public String getLOC() {

        return LOC;
    }

    public void setLOC(String LOC) {
        this.LOC = LOC;
    }
}
