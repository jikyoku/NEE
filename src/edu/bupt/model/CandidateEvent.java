package edu.bupt.model;

/**
 * Created by shixu on 2016-07-28.
 */
public class CandidateEvent {
    private int candidateEventId;
    private int triggerWordId;
    private int sentenceId;

    public int getCandidateEventId() {
        return candidateEventId;
    }

    public void setCandidateEventId(int candidateEventId) {
        this.candidateEventId = candidateEventId;
    }

    public int getTriggerWordId() {
        return triggerWordId;
    }

    public void setTriggerWordId(int triggerWordId) {
        this.triggerWordId = triggerWordId;
    }

    public int getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(int sentenceId) {
        this.sentenceId = sentenceId;
    }
}
