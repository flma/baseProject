package com.mfl.common.util;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.alibaba.fastjson.JSON;

public class RedisService implements IRedisService {
    private RedisTemplate<String, Serializable> redisTemplate;

    public RedisService(RedisTemplate<String, Serializable> redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <T> boolean add(String key, T obj) {
        // TODO Auto-generated method stub
        return add(key, JSON.toJSONString(obj));
    }

    @Override
    public boolean add(String key, String value) {
        // TODO Auto-generated method stub
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                // TODO Auto-generated method stub
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] object = serializer.serialize(value);
                return connection.setNX(keyStr, object);
            }
        });
        return result;
    }

    @Override
    public <T> boolean add(String key, List<T> list) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T> void add(String key, long timeout, T obj) {
        // TODO Auto-generated method stub

    }

    @Override
    public void add(String key, long timeout, byte[] object) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(String key) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(List<String> key) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean save(String key, String value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T> boolean save(String key, T obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clazz) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String get(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getByte(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> keys(String pattern) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean exist(String key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean set(String key, byte[] value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean flushDB() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public long dbSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * 获取 RedisSerializer
     */
    private RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }
}
