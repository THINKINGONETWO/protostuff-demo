package com.ha.net.protostuff.consumer.feign.client;
import com.ha.net.common.data.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author jiangwentao
 */
@FeignClient(name = "provider")
public interface ProviderClient {

    /**
     * @param user
     * @return
     */
    @RequestMapping(value = "/getUserInfoByUserName",produces = "application/x-protobuf")
    public User getUserInfo(User user);
}
