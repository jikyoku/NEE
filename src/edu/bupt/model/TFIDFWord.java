package edu.bupt.model;

/**
 * Created by shixu on 2016/11/15.
 */
public class TFIDFWord {

    private int id;
    private int dfCount;
    private int tfCount;
    private double tfIdf;
    private String word;

    public TFIDFWord() {
    }

    public TFIDFWord(int id, int dfCount, int tfCount, double tfIdf, String word) {
        this.id = id;
        this.dfCount = dfCount;
        this.tfCount = tfCount;
        this.tfIdf = tfIdf;
        this.word = word;
    }

    public TFIDFWord(int id, int tfCount, String word) {
        this.id = id;
        this.tfCount = tfCount;
        this.word = word;
    }

    public TFIDFWord(int dfCount, String word) {
        this.dfCount = dfCount;
        this.word = word;
    }

    public TFIDFWord(int id, double tfIdf, String word) {
        this.id = id;
        this.tfIdf = tfIdf;
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDfCount() {
        return dfCount;
    }

    public void setDfCount(int dfCount) {
        this.dfCount = dfCount;
    }

    public int getTfCount() {
        return tfCount;
    }

    public void setTfCount(int tfCount) {
        this.tfCount = tfCount;
    }

    public double getTfIdf() {
        return tfIdf;
    }

    public void setTfIdf(double tfIdf) {
        this.tfIdf = tfIdf;
    }
}
