package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert(){
        User user = new User();
        user.setUsername("TIM");
        user.setPassword("12345");
        Integer row = userMapper.insert(user);
        System.out.println(row);
    }

    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("tim");
        System.out.println(user);
    }
    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(7,"123","管理员", new Date());
    }
    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(7));
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(7);
        user.setPhone("15656666");
        user.setEmail("test002@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }
    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(9,"/upload/avatar.png","管理员",new Date());
    }

}
