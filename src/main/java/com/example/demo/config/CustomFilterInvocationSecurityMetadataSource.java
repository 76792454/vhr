package com.example.demo.config;


import com.example.demo.po.Menu;
import com.example.demo.po.Role;
import com.example.demo.serivce.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    MenuService menuService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    //Collection 当前请求需要的角色 Object实际上是一个filterInvocation对象
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //从filterInvocation里面可以获取当前请求的地址，拿到地址后，我就要拿这个地址去数据库里面跟这里的每一个菜单项去匹配，看是符合哪一个模式，然后再去看这个模式需要哪些角色
        String requestUrl = ((FilterInvocation)o).getRequestUrl();
        //这个方法每次请求都会调用
        List<Menu> menus = menuService.getAllMenusWithRole();
        //比较request跟menus里面的url是否一致 遍历menus 借助antPathMatcher工具进行
        for (Menu menu: menus) {
            //          String pattern:menus里面的规则
            //match1 = antPathMatcher.match("/freebytes/**", "/freebytes/1getA");
            //    boolean match2 = antPathMatcher.match("/freebytes/*/get*", "/freebytes/te/getA");
            //    boolean match3 = antPathMatcher.match("/freebytes/*/get*", "/freebytes/te/1getA");
            //    System.out.println(match1);     //true
            //    System.out.println(match2);     //true
            //    System.out.println(match3);     //false
            //}
            //关键是match方法，第一个参数写正则表达式，第二个参数写字符串。其实spring-security的登录拦截，也是基于这个工具类去实现的，
            // 可以看下security的应用代码，它的底层引用类就是 AntPathMatcher 。
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                List<Role> roles = menu.getRoles();
                String[] str = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    str[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(str);
            }
        }
        //      没匹配上的统一登录之后就可以访问  "ROLE_LOGIN"只是一个标记
        return SecurityConfig.createList("ROLE_LOGIN");
    }
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
