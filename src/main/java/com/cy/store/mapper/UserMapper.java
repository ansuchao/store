package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

//@Mapper
public interface UserMapper {

    Integer insert(User user);

    User findByUsername(String username);

   Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

   User findByUid(Integer uid);

   Integer updateInfoByUid(User user);
   /*修改用户的头像*/
   Integer updateAvatarByUid(@Param("uid") Integer uid,@Param("avatar") String avatar, @Param("modifiedUser")String modifiedUser,@Param("modifiedTime") Date modifiedTime);
}
