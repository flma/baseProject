package com.mfl.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.alibaba.dubbo.common.utils.StringUtils;

public class Realm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // TODO Auto-generated method stub
        // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
        if (SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principals);
            SecurityUtils.getSubject().logout();
            return null;
        }
        if (principals == null) {
            throw new AuthenticationException("parameters principals is null");
        }
        //获取已认证的用户名（登录名）
        String userId = (String) super.getAvailablePrincipal(principals);
        if(StringUtils.isBlank(userId)){
            return null;
        }
//        Set<String> roleCodes=roleService.getRoleCodeSet(userId);
//        //默认用户拥有所有权限
//        Set<String> functionCodes=functionService.getAllFunctionCode();
//       /* Set<String> functionCodes=functionService.getFunctionCodeSet(roleCodes);*/
//        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
//        authorizationInfo.setRoles(roleCodes);
//        authorizationInfo.setStringPermissions(functionCodes);
//        return authorizationInfo;
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        // TODO Auto-generated method stub
        return null;
    }

}
