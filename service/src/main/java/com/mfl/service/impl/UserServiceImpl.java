package com.mfl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfl.common.entity.User;
import com.mfl.common.service.IUserService;
import com.mfl.mapper.UserMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public User findById(long id) {
        // TODO Auto-generated method stub
        return userMapper.findById(id);
    }

    @Override
    public long insertUser(User user) {
        userMapper.insert(user);
        return user.getId();
    }


}
