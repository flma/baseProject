package service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mfl.common.entity.User;
import com.mfl.common.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
public class ConfigTest {
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testFindById() {
        Logger logger = org.apache.log4j.Logger.getLogger(ConfigTest.class);
        logger.info("fdsfafdf");

        User user = userService.findById(1);
        System.out.println(user.getAccount());
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setAccount("wangjie");
        userService.insertUser(user);
        System.out.println(user.getId());
    }

    @Test
    public void testReadis() {
        String key = "spring";
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set(key, "hihi");
    }

}
