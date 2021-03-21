package com.example.demo.serivce;

import com.example.demo.mapper.HrMapper;
import com.example.demo.po.Hr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HrService implements UserDetailsService {
    @Autowired
    HrMapper hrMapper;

    /**
     *
     * @param s 用户输入的用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(s);
      if(hr==null){
          throw new UsernameNotFoundException("用户名不存在");
      }
      return hr;
    }
}
