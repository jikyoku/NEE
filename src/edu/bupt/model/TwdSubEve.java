package edu.bupt.model;

import java.io.Serializable;

/**
 * Created by shixu on 2016-08-04.
 */
@SuppressWarnings("serial")
public class TwdSubEve extends TriggerWord implements Serializable{
    private SubEvent subEvent = null;

    public SubEvent getSubEvent() {
        return subEvent;
    }

    public void setSubEvent(SubEvent subEvent) {
        this.subEvent = subEvent;
    }
}
