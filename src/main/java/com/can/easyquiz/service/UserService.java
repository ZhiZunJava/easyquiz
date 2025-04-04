package com.can.easyquiz.service;

import com.can.easyquiz.domain.KeyValue;
import com.can.easyquiz.domain.User;
import com.can.easyquiz.viewmodel.admin.user.UserPageRequestVM;
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

    PageInfo<User> userPage(UserPageRequestVM requestVM);

    void insertUser(User user);

    void insertUsers(List<User> users);

    void updateUser(User user);

    void updateUsersAge(Integer age, List<Integer> ids);

    void deleteUserByIds(List<Integer> ids);

    Integer selectAllCount();

    List<KeyValue> selectByUserName(String userName);

    List<User> selectByIds(List<Integer> ids);

    void changePicture(User user, String imagePath);

    /**
     * 修改用户密码
     *
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return boolean 是否修改成功
     */
    boolean updatePassword(Integer userId, String oldPassword, String newPassword);
}
