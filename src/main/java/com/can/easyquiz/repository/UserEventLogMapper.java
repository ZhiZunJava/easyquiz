package com.can.easyquiz.repository;

import com.can.easyquiz.domain.KeyValue;
import com.can.easyquiz.domain.UserEventLog;
import com.can.easyquiz.viewmodel.admin.user.UserEventPageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Date;

@Mapper
public interface UserEventLogMapper extends BasicMapper<UserEventLog> {
    List<UserEventLogMapper> getUserEventLogByUserId(Integer id);

    List<UserEventLogMapper> page(UserEventPageRequest requestVM);

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
