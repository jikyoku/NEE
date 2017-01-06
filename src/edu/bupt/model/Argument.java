package edu.bupt.model;

/**
 * Created by shixu on 2016/12/22.
 */
public class Argument {
    private Integer id;
    private String type;
    private Integer beg;
    private Integer end;

    @Override
    public String toString() {
        return "Argument{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", beg=" + beg +
                ", end=" + end +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getBeg() {
        return beg;
    }

    public void setBeg(Integer beg) {
        this.beg = beg;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
}
