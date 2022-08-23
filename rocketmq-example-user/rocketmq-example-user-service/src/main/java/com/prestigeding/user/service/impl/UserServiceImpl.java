package com.prestigeding.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.prestigeding.user.config.TransactionMQProducerContainer;
import com.prestigeding.user.mapper.UserLoginLoggerMapper;
import com.prestigeding.user.mapper.UserMapper;
import com.prestigeding.user.model.User;
import com.prestigeding.user.model.UserLoginLogger;
import com.prestigeding.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserLoginLoggerMapper userLoginLoggerMapper;

    @Autowired
    private TransactionMQProducerContainer transactionMQProducerContainer;

    @Autowired
    private UserLoginTransactionListener userLoginTransactionListener;


    @Override
    public Map<String, Object> login(String userName, String password) {

        Map<String, Object> result = new HashMap<>();
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            result.put("code", 1);
            result.put("msg", "用户名与密码不能为空");
            return result;
        }
        try {

            User queryModel = new User();
            queryModel.setUsername(userName);
            User user = userMapper.selectOne(queryModel);


            if(user == null || !password.equals(user.getPassword()) ) {
                result.put("code", 1);
                result.put("msg", "用户名或密码不正确");
                return result;
            }
            //登录成功，记录登录日志
            UserLoginLogger userLoginLogger = new UserLoginLogger(user.getId(), new Date(System.currentTimeMillis()));
            userLoginLogger.setSerialNumber( UUID.randomUUID().toString().replace("-",""));

            //需要送积分
            if(enbaleActivity()) { //判断是否开启送积分活动，通常可以配置在apollo等配置中心中
               transactionMQProducerContainer.getTransactionMQProducer("user_login_topic", userLoginTransactionListener).sendMessageInTransaction(new Message("user_login_topic", JSON.toJSONString(userLoginLogger).getBytes()), null);
            }
            result.put("code", 0);
            result.put("data", user);
        } catch (Throwable e) {
            e.printStackTrace();
            result.put("code", 1);
            result.put("msg" , e.getMessage());
        }
        return result;
    }


    private boolean enbaleActivity() {
        return true;
    }
}
