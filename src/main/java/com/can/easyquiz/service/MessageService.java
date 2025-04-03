package com.can.easyquiz.service;

import com.can.easyquiz.domain.Message;
import com.can.easyquiz.domain.MessageUser;
import com.can.easyquiz.viewmodel.admin.message.MessagePageRequestVM;
import com.can.easyquiz.viewmodel.student.user.MessageRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MessageService {

    List<Message> selectMessageByIds(List<Integer> ids);

    PageInfo<MessageUser> studentPage(MessageRequestVM requestVM);

    PageInfo<Message> page(MessagePageRequestVM requestVM);

    List<MessageUser> selectByMessageIds(List<Integer> ids);

    void sendMessage(Message message, List<MessageUser> messageUsers);

    void read(Integer id);

    Integer unReadCount(Integer userId);

    Message messageDetail(Integer id);
}