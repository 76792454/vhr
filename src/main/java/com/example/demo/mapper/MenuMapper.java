package com.example.demo.mapper;

import com.example.demo.po.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Menu record);
    int insertSelective(Menu record);
    Menu selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Menu record);
    int updateByPrimaryKey(Menu record);
    List<Menu> getMenusByHrId(Integer hrId);
    List<Menu> getAllMenusWithRole();

}
