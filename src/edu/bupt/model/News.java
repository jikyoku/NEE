package edu.bupt.model;

import java.util.Date;
import java.util.List;

/**
 * Created by shixu on 2016-07-25.
 * @author shixu
 */
public class News {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String url;
    private String title;
    private String pubSource;
    private Date pubTime;
    private String content;
    private List<Sentence> sentenceList;

    private Sentence titleSent;

    public News(){
        titleSent = new Sentence(title);
    }

    public Sentence getTitleSent() {
        return titleSent;
    }

    public void setTitleSent(Sentence titleSent) {
        this.titleSent = titleSent;
    }

    public List<Sentence> getSentenceList() {
        return sentenceList;
    }

    public void setSentenceList(List<Sentence> sentenceList) {
        this.sentenceList = sentenceList;
    }

    @Override
    public String toString() {
        return "News{" +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", pubSource='" + pubSource + '\'' +
                ", pubTime=" + pubTime +
                ", content='" + content + '\'' +
                '}';
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubSource() {
        return pubSource;
    }

    public void setPubSource(String pubSource) {
        this.pubSource = pubSource;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
