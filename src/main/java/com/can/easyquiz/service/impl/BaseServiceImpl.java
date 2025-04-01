package com.can.easyquiz.service.impl;

import com.can.easyquiz.domain.UserEventLog;
import com.can.easyquiz.repository.BasicMapper;
import com.can.easyquiz.service.BasicService;
import com.can.easyquiz.viewmodel.admin.user.UserEventPageRequest;
import com.github.pagehelper.PageInfo;

public abstract class BaseServiceImpl<T> implements BasicService<T> {

    private final BasicMapper<T> baseMapper;

    public BaseServiceImpl(BasicMapper<T> baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public int deleteById(Integer id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T record) {
        return baseMapper.insert(record);
    }

    @Override
    public int insertByFilter(T record) {
        return baseMapper.insertSelective(record);
    }

    @Override
    public T selectById(Integer id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByIdFilter(T record) {
        return baseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateById(T record) {
        return baseMapper.updateByPrimaryKey(record);
    }

}
