package client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mfl.common.entity.User;
import com.mfl.common.service.IUserService;

public class TestMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:applicationContext*.xml");

        context.start();
        IUserService userService = context.getBean("userServiceImpl", IUserService.class);
        User user = userService.findById(1);
        System.out.println(user.getAccount());
        user = new User();
        user.setAccount("test");
        userService.insertUser(user);
    }
}
