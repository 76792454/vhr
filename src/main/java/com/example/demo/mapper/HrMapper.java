package com.example.demo.mapper;

import com.example.demo.po.Hr;
import com.example.demo.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface HrMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Hr record);
    int insertSelective(Hr record);
    Hr selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Hr record);
    int updateByPrimaryKey(Hr record);
    Hr loadUserByUsername(String username);
    List<Role> getHrRoleById(Integer id);
}
