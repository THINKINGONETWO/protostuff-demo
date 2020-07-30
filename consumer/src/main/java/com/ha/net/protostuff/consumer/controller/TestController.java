package com.ha.net.protostuff.consumer.controller;
import com.ha.net.common.data.User;
import com.ha.net.protostuff.consumer.feign.client.ProviderClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author jiangwentao
 */
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private ProviderClient providerClient;

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET,produces = "application/json")
    public User getUserInfo() {
        User user = new User();
        user.setUsername("xiaoming");

        user = providerClient.getUserInfo(user);
        logger.info("response user age :"+user.getAge());
        return user;
    }
}
