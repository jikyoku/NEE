package edu.bupt.model;

/**
 * Created by shi xu on 2016-07-30.
 *
 */
public class Feature {
    public Feature() {
    }

    public Feature( String triggerWord, String triggerWordPos, String before4pos, String before3pos, String before2pos, String before1pos, String after1pos, String after2pos, String after3pos, String after4pos, String before1EntityType, String after1EntityType) {
        this.triggerWord = triggerWord;
        this.triggerWordPos = triggerWordPos;
        this.before4pos = before4pos;
        this.before3pos = before3pos;
        this.before2pos = before2pos;
        this.before1pos = before1pos;
        this.after1pos = after1pos;
        this.after2pos = after2pos;
        this.after3pos = after3pos;
        this.after4pos = after4pos;
        this.before1EntityType = before1EntityType;
        this.after1EntityType = after1EntityType;

    }

    public final static String BEFORE = "before";
    public final static String AFTER = "after";

    private String label = "";
    private String sentence;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    private int eventId;
    private String triggerWord = "##";
    private String triggerWordPos = "##";
    private String before4pos = "##";
    private String before3pos = "##";
    private String before2pos = "##";
    private String before1pos = "##";
    private String after1pos = "##";
    private String after2pos = "##";
    private String after3pos = "##";
    private String after4pos = "##";
    private String before1EntityType = "##";
    private String after1EntityType = "##";
    private String code = "##";

    public String getTriggerWordPos() {
        return triggerWordPos;
    }

    public void setTriggerWordPos(String triggerWordPos) {
        this.triggerWordPos = triggerWordPos;
    }

    public  void setPOS(int postion,String direction,String content){
        switch (postion){
            case 1:
                if (direction == Feature.BEFORE)
                    setBefore1pos(content);
                else
                    setAfter1pos(content);
                break;
            case 2:
                if (direction == Feature.BEFORE)
                    setBefore2pos(content);
                else
                    setAfter2pos(content);
                break;
            case 3:
                if (direction == Feature.BEFORE)
                    setBefore3pos(content);
                else
                    setAfter3pos(content);
                break;
            case 4:
                if (direction == Feature.BEFORE)
                    setBefore4pos(content);
                else
                    setAfter4pos(content);
                break;
        }
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTriggerWord() {
        return triggerWord;
    }

    public void setTriggerWord(String triggerWord) {
        this.triggerWord = triggerWord;
    }


    public String getBefore4pos() {
        return before4pos;
    }

    public void setBefore4pos(String before4pos) {
        this.before4pos = before4pos;
    }

    public String getBefore3pos() {
        return before3pos;
    }

    public void setBefore3pos(String before3pos) {
        this.before3pos = before3pos;
    }

    public String getBefore2pos() {
        return before2pos;
    }

    public void setBefore2pos(String before2pos) {
        this.before2pos = before2pos;
    }

    public String getBefore1pos() {
        return before1pos;
    }

    public void setBefore1pos(String before1pos) {
        this.before1pos = before1pos;
    }

    public String getAfter1pos() {
        return after1pos;
    }

    public void setAfter1pos(String after1pos) {
        this.after1pos = after1pos;
    }

    public String getAfter2pos() {
        return after2pos;
    }

    public void setAfter2pos(String after2pos) {
        this.after2pos = after2pos;
    }

    public String getAfter3pos() {
        return after3pos;
    }

    public void setAfter3pos(String after3pos) {
        this.after3pos = after3pos;
    }

    public String getAfter4pos() {
        return after4pos;
    }

    public void setAfter4pos(String after4pos) {
        this.after4pos = after4pos;
    }

    public String getBefore1EntityType() {
        return before1EntityType;
    }

    public void setBefore1EntityType(String before1EntityType) {
        this.before1EntityType = before1EntityType;
    }

    public String getAfter1EntityType() {
        return after1EntityType;
    }

    public void setAfter1EntityType(String after1EntityType) {
        this.after1EntityType = after1EntityType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
