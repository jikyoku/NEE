package edu.bupt.model;

/**
 * Created by shixu on 2016-08-01.
 */
public class SubEvent {
    @Override
    public String toString() {
        return "SubEvent{" +
                "id=" + id +
                ", eventTypeId=" + eventTypeId +
                ", eventSubType='" + eventSubType + '\'' +
                '}';
    }

    private int id;
    private int eventTypeId;
    private String eventSubType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(int eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getEventSubType() {
        return eventSubType;
    }

    public void setEventSubType(String eventSubType) {
        this.eventSubType = eventSubType;
    }
}
