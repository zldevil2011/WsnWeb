package com.zhaolong.wsn.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Data")
public class Data {
	@Id
    @GeneratedValue
    private Long id;

    @Column(name = "created")
    private Long created = System.currentTimeMillis();

    @Column(name = "dataDate", nullable=true)
    private Date dataDate;
    
    @Column(name = "dataTime", nullable=true)
    private Time dataTime;
    
    @Column(name = "nodeId", nullable=true)
    private Long nodeId;
    

	@Column(name = "pm25")
    private Double pm25;

	@Column(name = "pm10")
    private Double pm10;

    @Column(name = "so2")
    private Double so2;

    @Column(name = "no2")
    private Double no2;
    
    @Column(name = "o3")
    private Double o3;
    
    @Column(name = "co")
    private Double co;
    
    @Column(name = "speed")
    private Double speed;
    
    @Column(name = "direction")
    private String direction;
    
    @Column(name = "humidity")
    private Double humidity;
    
    @Column(name = "aqi")
    private Double aqi;

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

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public Time getDataTime() {
		return dataTime;
	}

	public void setDataTime(Time dataTime) {
		this.dataTime = dataTime;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Double getPm25() {
		return pm25;
	}

	public void setPm25(Double pm25) {
		this.pm25 = pm25;
	}

	public Double getPm10() {
		return pm10;
	}

	public void setPm10(Double pm10) {
		this.pm10 = pm10;
	}

	public Double getSo2() {
		return so2;
	}

	public void setSo2(Double so2) {
		this.so2 = so2;
	}

	public Double getNo2() {
		return no2;
	}

	public void setNo2(Double no2) {
		this.no2 = no2;
	}

	public Double getO3() {
		return o3;
	}

	public void setO3(Double o3) {
		this.o3 = o3;
	}

	public Double getCo() {
		return co;
	}

	public void setCo(Double co) {
		this.co = co;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Double getAqi() {
		return aqi;
	}

	public void setAqi(Double aqi) {
		this.aqi = aqi;
	}
    
    
}
