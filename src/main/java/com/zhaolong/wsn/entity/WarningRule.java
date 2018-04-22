package com.zhaolong.wsn.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by zhaolong on 2018/4/22.
 */
/*
报警规则实体类
nodeId 设备ID
nodeName 设备名称
parameter 规则参数
ruleType 报警类型（采集异常0、超过阈值1、增长过快2）
ruleValue 报警值（None、阈值、增长率）
 */
@Entity
@Table(name = "WarningRule")
public class WarningRule {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "created")
    private Long created = System.currentTimeMillis();

    @Column(name = "nodeId")
    private Long nodeId;

    @Column(name = "nodeName")
    private String nodeName;

    @Column(name = "parameter")
    private String parameter;

    @Column(name = "ruleType")
    private int ruleType;

    @Column(name = "ruleValue")
    private Double ruleValue;

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

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public int getRuleType() {
        return ruleType;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public Double getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(Double ruleValue) {
        this.ruleValue = ruleValue;
    }
}
