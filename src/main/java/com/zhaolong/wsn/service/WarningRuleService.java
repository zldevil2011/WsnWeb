package com.zhaolong.wsn.service;

import com.zhaolong.wsn.entity.Event;
import com.zhaolong.wsn.entity.WarningRule;

import java.util.List;

public interface WarningRuleService {
    Long addWarningRule(WarningRule warningRule);
    List<WarningRule> warningRuleList();
}
