package com.mfl.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfl.common.entity.User;
import com.mfl.common.service.IUserService;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private IUserService userService;
    @RequestMapping("")
    public void test(){
        User user = new User();
        user.setAccount("test111");
      userService.insertUser(user);
        
    }
}
