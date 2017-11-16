package com.zhaolong.wsn.entity;

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

    @Column(name = "dataTime")
    private String dataTime;
    
    @Column(name = "nodeId")
    private Long nodeId;
    

	@Column(name = "pm25")
    private String pm25;

	@Column(name = "pm10")
    private String pm10;

    @Column(name = "so2")
    private String so2;

    @Column(name = "no2")
    private String no2;
    
    @Column(name = "o3")
    private String o3;
    
    @Column(name = "co")
    private String co;
    
    @Column(name = "speed")
    private String speed;
    
    @Column(name = "direcition")
    private String direcition;
    
    @Column(name = "humidity")
    private String humidity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public String getPm10() {
		return pm10;
	}

	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}

	public String getSo2() {
		return so2;
	}

	public void setSo2(String so2) {
		this.so2 = so2;
	}

	public String getNo2() {
		return no2;
	}

	public void setNo2(String no2) {
		this.no2 = no2;
	}

	public String getO3() {
		return o3;
	}

	public void setO3(String o3) {
		this.o3 = o3;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
    
	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getDirecition() {
		return direcition;
	}

	public void setDirecition(String direcition) {
		this.direcition = direcition;
	}
    
}
