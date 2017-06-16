package com.mfl.common.util;

import java.util.List;
import java.util.Set;

public interface IRedisService {
    /**
     * 插入缓存
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:06:16
     * @param key
     * @param Obj
     * @return
     */
    <T> boolean add(String key, T Obj);

    /**
     * 
     * 将字符串存入缓存
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:06:38
     * @param key
     * @param value
     * @return
     */
    boolean add(String key, String value);

    /**
     * 
     * 将list存入缓存
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:07:16
     * @param key
     * @param list
     * @return
     */
    <T> boolean add(String key, List<T> list);

    /**
     * 
     * 存入缓存，支持设置缓存时间
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:19:21
     * @param key
     * @param timeout
     * @param obj
     */
    <T> void add(String key, long timeout, T obj);

    /**
     * 
     * 存入缓存，支持设置缓存时间
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:19:21
     * @param key
     * @param timeout
     * @param obj
     */
    void add(String key, long timeout, byte[] object);

    /**
     * 根据key删除缓存
     * 
     * @author mafl@youwinedu.com
     * @date 2017年6月14日 上午11:13:24
     * @param key
     */
    void delete(String key);

    /**
     * 批量删除缓存
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:13:59
     * @param key
     */
    void delete(List<String> key);

    /**
     * 保存，存在则更新，不存在则创建
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:14:28
     * @param key
     * @param value
     * @return
     */
    boolean save(String key, String value);

    /**
     * 
     * 保存对象
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:15:17
     * @param key
     * @param obj
     * @return
     */
    <T> boolean save(String key, T obj);

    /**
     * 获取缓存
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:16:24
     * @param key
     * @param clazz
     * @return
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 获取缓存集合
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:17:16
     * @param key
     * @param clazz
     * @return
     */
    <T> List<T> getList(String key, Class<T> clazz);

    /**
     * 获取字符串类型
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:18:04
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 获取字节数组
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:18:22
     * @param key
     * @return
     */
    byte[] getByte(String key);

    /**
     * 
     * 获取键值集合
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:20:55
     * @param pattern
     * @return
     */
    Set<String> keys(String pattern);

    /**
     * 判断键值是否存在
     * 
     * @author mafengli
     * @date 2017年6月14日 上午11:22:00
     * @param key
     * @return
     */
    boolean exist(String key);

    boolean set(String key, byte[] value);

    boolean flushDB();

    long dbSize();
}
