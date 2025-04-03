package com.can.easyquiz.repository;

import com.can.easyquiz.domain.Message;
import com.can.easyquiz.viewmodel.admin.message.MessagePageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper extends BasicMapper<Message> {

    List<Message> page(MessagePageRequestVM requestVM);

    List<Message> selectByIds(List<Integer> ids);

    int readAdd(Integer id);
}