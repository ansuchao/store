package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        return new JsonResult<>(OK);
    }
    /*@RequestBody*/
    /*public JsonResult<Void> reg(User user){
       JsonResult<Void> result = new JsonResult<>();

        try {
            userService.reg(user);
            result.setState(2000);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicatedException e) {
           result.setState(4000);
           result.setMessage("用户名被占用");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return result;
    }*/
    @RequestMapping("login")
    public  JsonResult<User> login(String username, String password, HttpSession session){
        User data = userService.login(username,password);

        session.setAttribute("uid",data.getUid());
        session.setAttribute("username", data.getUsername());

        System.out.println(getuidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        return new JsonResult<User>(OK,data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword,HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }



    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
       User data = userService.getByUid(getuidFromSession(session));
       return new JsonResult<>(OK,data);
    }
    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);

        userService.changeInfo(uid,username,user);
        return new JsonResult<>(OK);
    }
    /*设计文件大小，存储大小*/
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }
    /**
     * MultipartFile接口是SpringMVC提供一个接口，这个接口为我们包装了
     * 获取文件类型的数据
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, @RequestParam("file") MultipartFile file){
              if (file.isEmpty()){
                   throw new FileEmptyException("文件为空");
              }
              if (file.getSize() > AVATAR_MAX_SIZE){
                  throw new FileSizeException("文件超出限制");
              }
              String contentType = file.getContentType();
              if (!AVATAR_TYPE.contains(contentType)){
                  throw new FileTypeException("文件类型不支持");
              }
              // 上传的文件
              String parent = session.getServletContext().getRealPath("upload");
              File dir = new File(parent);
              if (!dir.exists()){
                  dir.mkdirs();  //创建当前的目录
              }
              //UUID工具生成新的字符串作为文件名
              String originalFilename = file.getOriginalFilename();
              System.out.println("OriginalFilename=" + originalFilename);
              int index = originalFilename.lastIndexOf(".");
              String suffix = originalFilename.substring(index);
              String filename = UUID.randomUUID().toString().toUpperCase()+suffix;

              File dest = new File(dir, filename);
              try {
                 file.transferTo(dest);
              } catch (FileStateException e) {
                  throw new FileStateException("文件状态异常");

              } catch (IOException e) {
                 throw new FileUploadIOException("文件读写异常");
              }
              Integer uid = getuidFromSession(session);
              String username = getUsernameFromSession(session);
              String avatar = "/upload/" + filename;
              userService.changeAvatar(uid,avatar,username);
              return new JsonResult<>(OK,avatar);


    }

}
