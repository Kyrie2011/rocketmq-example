package com.prestigeding.gateway.control;


import com.alibaba.dubbo.config.annotation.Reference;
import com.prestigeding.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserControl {

    @Reference(check = false)
    private UserService userServiceProxy;


    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestParam("username") String username, @RequestParam("pwd") String password){

        Map<String, Object> result = new HashMap<String, Object>();

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            result.put("code", 1);
            result.put("msg", "用户名与密码不能为空");
            return result;
        }

        try {
            return userServiceProxy.login(username, password);
        } catch (Throwable e ) {
            e.printStackTrace();
            result.put("code", 1);
            result.put("msg" , e.getMessage());
        }

        return result;
    }






}
