package com.can.easyquiz.service.impl;

import com.can.easyquiz.config.property.SystemConfig;
import com.can.easyquiz.domain.KeyValue;
import com.can.easyquiz.domain.User;
import com.can.easyquiz.event.OnRegistrationCompleteEvent;
import com.can.easyquiz.exception.BusinessException;
import com.can.easyquiz.repository.UserMapper;
import com.can.easyquiz.service.UserService;
import com.can.easyquiz.utils.RsaUtil;
import com.can.easyquiz.viewmodel.admin.user.UserPageRequestVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final SystemConfig systemConfig;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, SystemConfig systemConfig, ApplicationEventPublisher eventPublisher) {
        super(userMapper);
        this.userMapper = userMapper;
        this.systemConfig = systemConfig;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<User> getUsers() {
        return userMapper.getAllUser();
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }

    @Override
    public int insertByFilter(User record) {
        return super.insertByFilter(record);
    }

    @Override
    public int updateByIdFilter(User record) {
        return super.updateByIdFilter(record);
    }

    @Override
    public int updateById(User record) {
        return super.updateById(record);
    }

    @Override
    public User getUserByUserNamePwd(String username, String pwd) {
        return userMapper.getUserByUserNamePwd(username, pwd);
    }

    @Override
    public User getUserByUuid(String uuid) {
        return userMapper.getUserByUuid(uuid);
    }

    @Override
    public List<User> userPageList(String name, Integer pageIndex, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("name", name);
        map.put("offset", ((int) pageIndex) * pageSize);
        map.put("limit", pageSize);
        return userMapper.userPageList(map);
    }

    @Override
    public Integer userPageCount(String name) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("name", name);
        return userMapper.userPageCount(map);
    }


    @Override
    public PageInfo<User> userPage(UserPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                userMapper.userPage(requestVM)
        );
    }


    @Override
    public void insertUser(User user) {
        userMapper.insertSelective(user);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void insertUsers(List<User> users) {
        userMapper.insertUsers(users);
        throw new BusinessException("test BusinessException roll back");
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void updateUsersAge(Integer age, List<Integer> ids) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("idslist", ids);
        map.put("age", age);
        userMapper.updateUsersAge(map);
    }

    @Override
    public void deleteUserByIds(List<Integer> ids) {
        userMapper.deleteUsersByIds(ids);
    }

    @Override
    public Integer selectAllCount() {
        return userMapper.selectAllCount();
    }

    @Override
    public List<KeyValue> selectByUserName(String userName) {
        return userMapper.selectByUserName(userName);
    }

    @Override
    public List<User> selectByIds(List<Integer> ids) {
        return userMapper.selectByIds(ids);
    }

    @Override
    @Transactional
    public void changePicture(User user, String imagePath) {
        User changePictureUser = new User();
        changePictureUser.setId(user.getId());
        changePictureUser.setImagePath(imagePath);
        userMapper.updateByPrimaryKeySelective(changePictureUser);
    }

    @Override
    @Transactional
    public boolean updatePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userMapper.getUserById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 验证旧密码
        String decodedOldPassword = RsaUtil.rsaDecode(systemConfig.getPwdKey().getPrivateKey(), oldPassword);
        if (!oldPassword.equals(decodedOldPassword)) {
            return false;
        }

        // 加密新密码
        String encodedNewPassword = RsaUtil.rsaEncode(systemConfig.getPwdKey().getPublicKey(), newPassword);
        user.setPassword(encodedNewPassword);
        user.setModifyTime(new Date());
        
        // 更新密码
        userMapper.updateUser(user);
        
        // 发布密码修改事件
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
        
        return true;
    }
}
