package edu.bupt.model;

/**
 * Created by shixu on 2017/1/4.
 */
public class Event {
    private String LOC;
    private String A1;
    private String A0;
    private String TMP;
    private String HED;

    @Override
    public String toString() {
        return "Event{" +
                "LOC='" + LOC + '\'' +
                ", A1='" + A1 + '\'' +
                ", A0='" + A0 + '\'' +
                ", TMP='" + TMP + '\'' +
                ", HED='" + HED + '\'' +
                '}';
    }

    public String getHED() {
        return HED;
    }

    public void setHED(String HED) {
        this.HED = HED;
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
