package com.mfl.mapper;

import com.mfl.common.entity.User;

public interface UserMapper {
    User findById(long id);
    long insert(User user);
}
