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
    

	@Column(name = "pm25", nullable=true)
    private double pm25;

	@Column(name = "pm10", nullable=true)
    private double pm10;

    @Column(name = "so2", nullable=true)
    private double so2;

    @Column(name = "no2", nullable=true)
    private double no2;
    
    @Column(name = "o3", nullable=true)
    private double o3;
    
    @Column(name = "co", nullable=true)
    private double co;
    
    @Column(name = "speed", nullable=true)
    private double speed;
    
    @Column(name = "direction", nullable=true)
    private String direction;
    
    @Column(name = "humidity", nullable=true)
    private double humidity;
    
    @Column(name = "aqi", nullable=true)
    private double aqi;

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

	public double getPm25() {
		return pm25;
	}

	public void setPm25(double pm25) {
		this.pm25 = pm25;
	}

	public double getPm10() {
		return pm10;
	}

	public void setPm10(double pm10) {
		this.pm10 = pm10;
	}

	public double getSo2() {
		return so2;
	}

	public void setSo2(double so2) {
		this.so2 = so2;
	}

	public double getNo2() {
		return no2;
	}

	public void setNo2(double no2) {
		this.no2 = no2;
	}

	public double getO3() {
		return o3;
	}

	public void setO3(double o3) {
		this.o3 = o3;
	}

	public double getCo() {
		return co;
	}

	public void setCo(double co) {
		this.co = co;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public String getDirecition() {
		return direction;
	}

	public void setDirection(String direcition) {
		this.direction = direcition;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public double getAqi() {
		return aqi;
	}

	public void setAqi(double aqi) {
		this.aqi = aqi;
	}
	
    
}
