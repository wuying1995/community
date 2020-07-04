package com.example.demo.service;


import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user1) {

        User dbuser= userMapper.findByAccountId(user1.getAccountId());

        if (dbuser==null){

            user1.setGmtCreate(System.currentTimeMillis());
            user1.setGmtModified(user1.getGmtCreate());

            userMapper.insert(user1);
        }else {

            user1.setGmtModified(user1.getGmtCreate());
            dbuser.setAvatarUrl(user1.getAvatarUrl());
            dbuser.setName(user1.getName());
            dbuser.setToken(user1.getToken());
            userMapper.update(dbuser);

        }


    }
}
