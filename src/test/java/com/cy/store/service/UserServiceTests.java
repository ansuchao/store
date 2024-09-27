package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired
    private IUserService userMapper;


    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("admin01");
            user.setPassword("123456");
            userMapper.reg(user);
            System.out.println("ok");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User user = userMapper.login("admin","123456");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        userMapper.changePassword(9,"管理员","123456","123");
    }

    @Test
    public void getByUid(){
        System.err.println(userMapper.getByUid(7));
    }

    @Test
    public void changeInfo(){
        User user = new User();
        user.setPhone("1222222");
        user.setEmail("shgsh@qq.com");
        user.setGender(0);
        userMapper.changeInfo(7,"管理员",user);
    }
    @Test
    public void changeAvatar(){
        userMapper.changeAvatar(9,"/uplod/test.png","小明");
    }
}
