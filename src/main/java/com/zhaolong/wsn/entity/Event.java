package com.zhaolong.wsn.entity;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Event")
public class Event {
	@Id
    @GeneratedValue
    private Long id;

    @Column(name = "created")
    private Long created = System.currentTimeMillis();
    
    @Column(name = "nodeId")
    private Long nodeId;
    
    @Column(name = "eventType")
    private int eventType;
    
    @Column(name = "eventName")
    private String eventName;
    
    @Column(name = "eventDesc")
    private String eventDesc;
    
    @Column(name = "eventDate")
    private Date eventDate;
    
    @Column(name = "eventTime")
    private Time eventTime;
    
    @Column(name = "eventLevel")
    private int eventLevel;
    
    @Column(name = "eventLevelDesc")
    private String eventLevelDesc;
    
    @Column(name = "eventOpe")
    private String eventOpe;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Time getEventTime() {
		return eventTime;
	}

	public void setEventTime(Time eventTime) {
		this.eventTime = eventTime;
	}

	public int getEventLevel() {
		return eventLevel;
	}

	public void setEventLevel(int eventLevel) {
		this.eventLevel = eventLevel;
	}

	public String getEventLevelDesc() {
		return eventLevelDesc;
	}

	public void setEventLevelDesc(String eventLevelDesc) {
		this.eventLevelDesc = eventLevelDesc;
	}

	public String getEventOpe() {
		return eventOpe;
	}

	public void setEventOpe(String eventOpe) {
		this.eventOpe = eventOpe;
	}
    
    
    
}
