package com.can.easyquiz.service;

import com.can.easyquiz.domain.UserEventLog;
import com.can.easyquiz.repository.UserEventLogMapper;
import com.can.easyquiz.viewmodel.admin.user.UserEventPageRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserEventLogService extends BasicService<UserEventLog> {
    List<UserEventLogMapper> getUserEventLogByUserId(Integer id);

    PageInfo<UserEventLog> page(UserEventPageRequest requestVM);

    List<Integer> selectMothCount();
}
