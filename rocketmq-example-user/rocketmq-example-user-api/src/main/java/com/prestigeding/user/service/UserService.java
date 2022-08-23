package com.prestigeding.user.service;

import com.prestigeding.user.dto.UserDto;

import java.util.Map;

/**
 * dubbo 服务
 */
public interface UserService {


    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    public Map<String, Object> login(String userName, String password);


}
