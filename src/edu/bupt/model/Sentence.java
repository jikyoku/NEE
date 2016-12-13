package edu.bupt.model;

import java.util.List;

/**
 * Created by shi xu on 2016-07-25.
 *
 */
public class Sentence {
    private int id;
    private int articleId;
    private String sentenceContent;
    private String[] words;
    private String[] taggers;
    private List<Word> wordList;
    private List<Entity> entityList;

    public List<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public Sentence(String sentenceContent){
        this.sentenceContent = sentenceContent;
    }
    public Sentence(){}

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }

    public String[] getTaggers() {
        return taggers;
    }

    public void setTaggers(String[] taggers) {
        this.taggers = taggers;
    }

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

    public String getSentenceContent() {
        return sentenceContent;
    }

    public void setSentenceContent(String sentenceContent) {
        this.sentenceContent = sentenceContent;
    }

}
