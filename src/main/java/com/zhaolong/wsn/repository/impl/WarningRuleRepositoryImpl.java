package com.zhaolong.wsn.repository.impl;

import com.zhaolong.wsn.entity.WarningRule;
import com.zhaolong.wsn.repository.WarningRuleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zhaolong on 2018/4/22.
 */
public class WarningRuleRepositoryImpl implements WarningRuleRepository {

    @Autowired
    private SessionFactory sessionFactory;
    private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }

    public WarningRule load(Long id) {
        return null;
    }

    public WarningRule get(Long id) {
        return null;
    }

    public List<WarningRule> findAll() {
        return null;
    }

    public void persist(WarningRule entity) {

    }

    public Long save(WarningRule entity) {
        Session currentSession = getCurrentSession();
        Long res = (Long)getCurrentSession().save(entity);
        currentSession.close();
        return res;
    }

    public void saveOrUpdate(WarningRule entity) {

    }

    public void delete(Long id) {

    }

    public void flush() {

    }
}
