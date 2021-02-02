package com.example.springsecurity.server.serverImpl;

import com.example.springsecurity.dao.TestUserMapper;
import com.example.springsecurity.entity.TestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestUserServer implements UserDetailsService {
    @Autowired
    private TestUserMapper testUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //username参数,是在登陆时,用户传递的表单数据username
        //主要读取数据库3个值 username password authorities
        TestUser testUser = testUserMapper.selectOne(username);
        String authorityName = testUser.getAuthority();
        //为了返回一个UserDetails 使用User
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(authorityName);
        authorities.add(authority);
        //这里的User 是这个包下的 org.springframework.security.core.userdetails.User;
        return new User(
                testUser.getUsername(),
                testUser.getPassword(),
                authorities);
    }

}
