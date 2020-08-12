package com.ha.net.protostuff.provider.controller;

import com.ha.net.common.api.ProviderApi;
import com.ha.net.common.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author jiangwentao
 */
@RestController
public class UserController implements ProviderApi {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    @RequestMapping(value = "/getUserInfoByUserName")
    public User getUserInfo(@RequestBody User user) {

        String name = user.getUsername();
        logger.info("request username :"+name);
        if ("xiaoming".equals(name)) {
            user.setAge(18);
            user.setAddress("GuangDong Province");
            user.setPassword("123456");
        } else {
            user.setAge(100);
            user.setAddress("Beijing");
            user.setPassword("654321");
        }
        logger.info("user age : "+user.getAge());

        return user;
    }
}
