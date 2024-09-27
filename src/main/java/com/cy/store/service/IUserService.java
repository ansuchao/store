package com.cy.store.service;


import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface IUserService {

    void reg(User user);

    User login(String usernsme,String password);

    void changePassword(Integer uid,String username,String oldPassword,String newPassword);

    /*根据用户的id查询用户的数据*/
    User getByUid(Integer uid);

    void changeInfo(Integer uid, String username, User user);

    /**
     * 修改用户的头像
     * @param uid 用户的id
     * @param avatar 用户头像的路径
     * @param username 用户的名称
     */
    void changeAvatar(Integer uid,String avatar,String username);
}
