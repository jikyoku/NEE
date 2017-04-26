package edu.bupt.model;

/**
 * Created by shixu on 2016-07-25.
 */
public class Segment {
    private int id;
    private int articleId;
    private String segmentContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getSegmentContent() {
        return segmentContent;
    }

    public void setSegmentContent(String segmentContent) {
        this.segmentContent = segmentContent;
    }
}
