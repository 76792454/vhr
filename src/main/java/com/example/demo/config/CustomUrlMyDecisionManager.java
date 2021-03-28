package com.example.demo.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomUrlMyDecisionManager implements AccessDecisionManager {
    /**
     *
     * @param authentication 当前登录的用户
     * @param o 请求对象
     * @param collection 是CustomFilterInvocationSecurityMetadataSource类中的getAttributes方法的返回值
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    //很好比对，用户的角色在authentication里面，需要的角色在configAttributes里面，再区比较他们俩集合里面有没有包含关系就行
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        //遍历需要的角色
        for (ConfigAttribute configAttribute:collection) {
            //他需要的角色
            String needRole = configAttribute.getAttribute();
            //如果他需要的角色是ROLE_LOGIN
            if("ROLE_LOGIN".equals(needRole)){
                //如果当前用户是匿名用户的实例的话，就是没有登录
                if(authentication instanceof AnonymousAuthenticationToken){
                    //没登录抛出异常
                    throw new AccessDeniedException("尚未登录，请登录!");
                }else{
                    return;
                }
            }
            //获取当前登录用户的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority: authorities) {
                //如果这两个是相等的
                if(authority.getAuthority().equals(needRole)){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足,请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
