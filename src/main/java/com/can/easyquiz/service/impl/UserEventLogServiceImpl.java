package com.can.easyquiz.service.impl;

import com.can.easyquiz.domain.KeyValue;
import com.can.easyquiz.domain.UserEventLog;
import com.can.easyquiz.repository.UserEventLogMapper;
import com.can.easyquiz.service.UserEventLogService; // 正确导入接口
import com.can.easyquiz.utils.DateTimeUtil;
import com.can.easyquiz.viewmodel.admin.user.UserEventPageRequestVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // 导入 Service 注解

import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class UserEventLogServiceImpl extends BaseServiceImpl<UserEventLog> implements UserEventLogService {
    private final UserEventLogMapper userEventLogMapper;

    @Autowired
    public UserEventLogServiceImpl(UserEventLogMapper userEventLogMapper) {
        super(userEventLogMapper);
        this.userEventLogMapper = userEventLogMapper;
    }

    @Override
    public List<UserEventLog> getUserEventLogByUserId(Integer id) {
        return userEventLogMapper.getUserEventLogByUserId(id);
    }

    @Override
    public PageInfo<UserEventLog> page(UserEventPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                userEventLogMapper.page(requestVM)
        );
    }


    @Override
    public List<Integer> selectMothCount() {
        Date startTime = DateTimeUtil.getMonthStartDay();
        Date endTime = DateTimeUtil.getMonthEndDay();
        List<KeyValue> mouthCount = userEventLogMapper.selectCountByDate(startTime, endTime);
        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
        return mothStartToNowFormat.stream().map(md -> {
            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());
    }
}