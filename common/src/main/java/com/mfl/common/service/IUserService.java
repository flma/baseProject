package com.mfl.common.service;

import com.mfl.common.entity.User;

public interface IUserService {
    User findById(long id);
    long insertUser(User user);
}
