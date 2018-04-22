package com.zhaolong.wsn.service.impl;

import com.zhaolong.wsn.entity.WarningRule;
import com.zhaolong.wsn.repository.WarningRuleRepository;
import com.zhaolong.wsn.service.WarningRuleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaolong on 2018/4/22.
 */
public class WarningRuleServiceImpl implements WarningRuleService {
    @Autowired
    private WarningRuleRepository warningRuleRepository;

    public Long addWarningRule(WarningRule warningRule) {
        return warningRuleRepository.save(warningRule);
    }
}
