package com.mfl.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

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
        return this.add(key, list);
    }

    @Override
    public <T> void add(String key, long timeout, T obj) {
        // TODO Auto-generated method stub
        redisTemplate.execute(new RedisCallback<T>() {

            @Override
            public T doInRedis(RedisConnection arg0) throws DataAccessException {
                // TODO Auto-generated method stub
                RedisSerializer<String> serializer = getRedisSerializer();
                final byte[] object = serializer.serialize(JSON.toJSONString(obj));
                add(key, timeout, object);
                return null;
            }

        });
    }

    @Override
    public void add(String key, long timeout, byte[] object) {
        // TODO Auto-generated method stub
        redisTemplate.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                // TODO Auto-generated method stub
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                connection.setEx(keyStr, timeout, object);
                return null;
            }
        });
    }

    @Override
    public void delete(String key) {
        // TODO Auto-generated method stub
        List<String> list = new ArrayList<>();
        list.add(key);
        this.delete(key);
    }

    @Override
    public void delete(List<String> key) {
        // TODO Auto-generated method stub
        redisTemplate.delete(key);
    }

    @Override
    public <T> boolean update(String key, T obj) {
        // TODO Auto-generated method stub
        return this.update(key, JSON.toJSONString(obj));
    }

    @Override
    public boolean update(String key, String value) {
        // TODO Auto-generated method stub
        if (get(key) == null) {
            throw new NullPointerException("数据行不存在，key=" + key);
        }
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                // TODO Auto-generated method stub
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] valueStr = serializer.serialize(value);
                connection.set(keyStr, valueStr);
                return true;
            }
        });
        return result;
    }

    @Override
    public boolean save(String key, String value) {
        // TODO Auto-generated method stub
        if (StringUtils.isEmpty(get(key))) {
            return this.add(key, value);
        }
        else {
            return this.update(key, value);
        }
    }

    @Override
    public <T> boolean save(String key, T obj) {
        // TODO Auto-generated method stub
        if (get(key, obj.getClass()) == null) {
            return this.add(key, obj);
        }
        else {
            return this.update(key, obj);
        }
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        // TODO Auto-generated method stub
        T result = redisTemplate.execute(new RedisCallback<T>() {

            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                // TODO Auto-generated method stub
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] value = connection.get(keyStr);
                if (value == null) {
                    return null;
                }
                String valueStr = serializer.deserialize(value);
                return (T) JSON.parseObject(valueStr, clazz);
            }
        });
        return result;
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clazz) {
        // TODO Auto-generated method stub
        List<T> result = redisTemplate.execute(new RedisCallback<List<T>>() {

            @Override
            public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
                // TODO Auto-generated method stub
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] value = connection.get(keyStr);
                if (value == null) {
                    return null;
                }
                String valueStr = serializer.deserialize(value);
                return JSON.parseArray(valueStr,clazz);
            }
        });
        return result;
    }

    @Override
    public String get(String key) {
        // TODO Auto-generated method stub
        String result = redisTemplate.execute(new RedisCallback<String>() {

            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                // TODO Auto-generated method stub
                RedisSerializer<String> serializer=getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] value = connection.get(keyStr);
                if(value==null){
                    return null;
                }
                String valueStr = serializer.deserialize(value);
                return valueStr;
            }
        });
        return result;
    }

    @Override
    public byte[] getByte(String key) {
        // TODO Auto-generated method stub
        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {

            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                // TODO Auto-generated method stub
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] value = connection.get(keyStr);
                return value;
            }
        });
        return result;
    }

    @Override
    public Set<String> keys(String pattern) {
        // TODO Auto-generated method stub
        return redisTemplate.keys(pattern);
    }

    @Override
    public boolean exist(String key) {
        // TODO Auto-generated method stub
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                // TODO Auto-generated method stub
                RedisSerializer<String> serializer=getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                return connection.exists(keyStr);
            }
        });
        return result;
    }

    @Override
    public boolean set(String key, byte[] value) {
        // TODO Auto-generated method stub
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                // TODO Auto-generated method stub
                RedisSerializer<String> serializer=getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                connection.set(keyStr, value);
                return true;
            }
        });
        return result;
    }

    @Override
    public boolean flushDB() {
        // TODO Auto-generated method stub
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                // TODO Auto-generated method stub
                connection.flushDb();
                return true;
            }
        });
        return result;
    }

    @Override
    public long dbSize() {
        // TODO Auto-generated method stub
        long result = redisTemplate.execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                // TODO Auto-generated method stub
                return connection.dbSize();
            }
        });
        return result;
    }

    /**
     * 获取 RedisSerializer
     */
    private RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }
}
