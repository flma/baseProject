package com.mfl.shiro;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.mfl.common.util.Constants;

import jline.internal.Log;

public class RedisSessionDao extends AbstractSessionDAO {
    private static final Logger logger = Logger.getLogger(RedisSessionDao.class);
    @Value("${session.expire}")
    private long expire;
    @Autowired
    private RedisTemplate redisTemplate;
    private int i = 0;

    @Override
    public void update(Session session) throws UnknownSessionException {
        // TODO Auto-generated method stub
        this.saveSession(session);
    }

    /**
     * 保存session
     * 
     * @date 2017年6月13日 下午5:25:38
     * @param session
     */
    @SuppressWarnings("unchecked")
    private void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            Log.error("session or session id is null");
            return;
        }
        session.setTimeout(expire);
        long timeOut = expire / 1000;
        ValueOperations vo = redisTemplate.opsForValue();
        //保存用户会话
        vo.set(this.getKey(Constants.Redis.SHIRO_REDIS_SESSION_PRE, session.getId().toString()),
                session, timeOut, TimeUnit.SECONDS);
        String uid = getUserId(session);
        if (StringUtils.isNotBlank(uid)) {
            try {
                //保存会话对应的UID
                vo.set(this.getKey(Constants.Redis.SHIRO_SESSION_PRE, session.getId().toString()),
                        uid.getBytes("UTF-8"), timeOut);
                //保存在线UID
                vo.set(this.getKey(Constants.Redis.UID_PRE, uid),
                        ("online" + (i++) + "").getBytes("UTF-8"), timeOut);
            }
            catch (UnsupportedEncodingException e) {
                // TODO: handle exception
                Log.error("getBytes error:" + e.getMessage());
            }

        }
    }

    private String getUserId(Session session) {
        SimplePrincipalCollection pricipal = (SimplePrincipalCollection) session.getAttribute(
                "org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
        if (pricipal != null) {
            return pricipal.getPrimaryPrincipal().toString();
        }
        return null;

    }

    public String getKey(String prefix, String keyStr) {
        return prefix + keyStr;
    }

    @Override
    public void delete(Session session) {
        // TODO Auto-generated method stub
        if (session == null || session.getId() == null) {
            Log.error("session or session id is null");
            return;
        }
        //删除用户会话
        redisTemplate.delete(
                this.getKey(Constants.Redis.SHIRO_REDIS_SESSION_PRE, session.getId().toString()));
        //获取缓存的用户会话对应的uid
        String uid = (String) redisTemplate.opsForValue()
                .get(this.getKey(Constants.Redis.SHIRO_SESSION_PRE, session.getId().toString()));
        //删除用户会话sessionid对应的uid
        redisTemplate
                .delete(this.getKey(Constants.Redis.SHIRO_SESSION_PRE, session.getId().toString()));
        //删除在线uid
        redisTemplate.delete(this.getKey(Constants.Redis.UID_PRE, uid));
        //删除用户缓存的角色
        redisTemplate.delete(this.getKey(Constants.Redis.ROLE_PRE, uid));
        //删除用户缓存的权限
        redisTemplate.delete(this.getKey(Constants.Redis.PERMISSION_PRE, uid));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        // TODO Auto-generated method stub
        Set<Session> sessions = new HashSet<>();
        Set<String> keys = redisTemplate.keys(Constants.Redis.SHIRO_REDIS_SESSION_PRE + "*");
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                Session s = (Session) redisTemplate.opsForValue().get(key);
                sessions.add(s);
            }
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        // TODO Auto-generated method stub
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        // TODO Auto-generated method stub
        if (sessionId == null) {
            logger.error("session is null");
            return null;
        }
        logger.debug("Read Redis.SessionId=" + new String(
                getKey(Constants.Redis.SHIRO_REDIS_SESSION_PRE, sessionId.toString())));
        Session session = (Session) redisTemplate.opsForValue()
                .get(getKey(Constants.Redis.SHIRO_SESSION_PRE, sessionId.toString()));
        return session;
    }

    /**
     * 当前用户是否在线
     *
     * @param uid 用户id
     * @return
     */
    public boolean isOnLine(String uid) {
        Set<String> keys = redisTemplate.keys(Constants.Redis.UID_PRE + uid);
        if (keys != null && keys.size() > 0) {
            return true;
        }
        return false;
    }
}
