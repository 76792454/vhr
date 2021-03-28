package com.example.demo.serivce;


import com.example.demo.mapper.MenuMapper;
import com.example.demo.po.Hr;
import com.example.demo.po.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;
    public List<Menu> getMenusByHrId(){
        //要传入id了，id从哪里来，我们登录的用户信息保存到security里面
        //SecurityContextHolder里面有一个getContext()方法.getAuthentication()它里面的getPrincipal()，Principal它是当前登录的用户对象，然后强转成Hr对象再获取它里面的id
        return menuMapper.getMenusByHrId(((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }
    //获取所有菜单角色，一对多，一个菜单项有多个角色
    public List<Menu> getAllMenusWithRole(){
        return menuMapper.getAllMenusWithRole();
    }
}
