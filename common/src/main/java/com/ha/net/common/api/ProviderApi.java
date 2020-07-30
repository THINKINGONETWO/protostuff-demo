package com.ha.net.common.api;

import com.ha.net.common.data.User;

/**
 * @author jiangwentao
 */
public interface ProviderApi {

    /**
     * @param user
     * @return
     */
    public User getUserInfo(User user);
}
