package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void reg(User user) {
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        if (result != null){
            throw new UsernameDuplicatedException("用户名被占用");
        }

        String oldPassword = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        String md5Password = getMD5Password(oldPassword,salt);
        user.setPassword(md5Password);

        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);



        Integer rows = userMapper.insert(user);
        if(rows != 1){
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
    }

    @Override
    public User login(String usernsme, String password) {
        User result = userMapper.findByUsername(usernsme);
        if (result == null){
            throw new UserNotFoundException("用户数据不存在");
        }

        String oldPassword = result.getPassword();
        String salt = result.getSalt();

        String newMd5Password = getMD5Password(password, salt);
        if (!newMd5Password.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }

        if (result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }

        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数值不存在");
        }
        //原始密码和数据库中密码进行比较
      String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
        //将新的密码设置到数据库中，将新的密码进行加密再去更新
        String newMd5Password = getMD5Password(newPassword,result.getSalt());
       Integer rows = userMapper.updatePasswordByUid(uid,newMd5Password,username,new Date());
       if (rows != 1){
           throw new UpdateException("更新数据产生未知的异常");
       }
    }

    @Override
    public User getByUid(Integer uid) {
       User result = userMapper.findByUid(uid);
       if (result==null || result.getIsDelete()==1){
           throw new UserNotFoundException("用户数据不存在");
       }
       User user = new User();
       user.setUsername(result.getUsername());
       user.setPhone(result.getPhone());
       user.setEmail(result.getEmail());
       user.setGender(result.getGender());
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        //user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1){
            throw new UpdateException("更新数据时产生未知的异常");
        }

    }

    /**
     * 修改用户的头像
     *
     * @param uid      用户的id
     * @param avatar   用户头像的路径
     * @param username 用户的名称
     */
    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if (result==null || result.getIsDelete().equals(1)){
            throw new UserNotFoundException("用户数据不存在");
        }
       Integer rows = userMapper.updateAvatarByUid(uid,avatar,username,new Date());
        if(rows != 1){
            throw new UpdateException("更新用户头像产生未知的异常");
        }

    }


    /*定义一个md5算法的加密处理*/
    private String getMD5Password(String password,String salt){
        for(int i = 0; i<3;i++){
           password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
