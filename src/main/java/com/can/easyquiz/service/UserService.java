package com.can.easyquiz.service;

import com.can.easyquiz.domain.KeyValue;
import com.can.easyquiz.domain.User;
import com.can.easyquiz.viewmodel.admin.user.UserPageRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService extends BasicService<User> {
    List<User> getUsers();

    User getUserById(Integer id);

    User getUserByUserName(String username);

    User getUserByUserNamePwd(String username, String pwd);

    User getUserByUuid(String uuid);

    List<User> userPageList(String name, Integer pageIndex, Integer pageSize);

    Integer userPageCount(String name);

    PageInfo<User> userPage(UserPageRequest requestVM);

    void insertUser(User user);

    void insertUsers(List<User> users);

    void updateUser(User user);

    void updateUsersAge(Integer age, List<Integer> ids);

    void deleteUserByIds(List<Integer> ids);

    Integer selectAllCount();

    List<KeyValue> selectByUserName(String userName);

    List<User> selectByIds(List<Integer> ids);

    void changePicture(User user, String imagePath);
}
