package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mfl.common.entity.User;
import com.mfl.common.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
public class ConfigTest {
    @Autowired
    private IUserService userService;

    @Test
    public void testFindById() {
        User user = userService.findById(1);
        System.out.println(user.getAccount());
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setAccount("wangjie");
        userService.insertUser(user);
        System.out.println(user.getId());
    }
}
