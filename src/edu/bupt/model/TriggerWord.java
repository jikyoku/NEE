package edu.bupt.model;

/**
 * Created by shixu on 2016-07-28.
 */
public class TriggerWord {
    @Override
    public String toString() {
        return "TriggerWord{" +
                "triggerWordId=" + triggerWordId +
                ", eventSubTypeId=" + eventSubTypeId +
                ", triggerWordContent='" + triggerWordContent + '\'' +
                ", subEvent=" + subEvent +
                '}';
    }

    private int triggerWordId;
    private int eventSubTypeId;
    private String triggerWordContent;


    private SubEvent subEvent;

    public SubEvent getSubEvent() {
        return subEvent;
    }

    public void setSubEvent(SubEvent subEvent) {
        this.subEvent = subEvent;
    }

    public int getTriggerWordId() {
        return triggerWordId;
    }

    public void setTriggerWordId(int triggerWordId) {
        this.triggerWordId = triggerWordId;
    }

    public String getTriggerWordContent() {
        return triggerWordContent;
    }

    public void setTriggerWordContent(String triggerWordContent) {
        this.triggerWordContent = triggerWordContent;
    }

    public int getEventSubTypeId() {

        return eventSubTypeId;
    }

    public void setEventSubTypeId(int eventSubTypeId) {
        this.eventSubTypeId = eventSubTypeId;
    }
}
