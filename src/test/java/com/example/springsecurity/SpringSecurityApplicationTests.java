package com.example.springsecurity;

import com.example.springsecurity.dao.TestUserMapper;
import com.example.springsecurity.entity.TestUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootTest(classes = SpringSecurityApplication.class)
class SpringSecurityApplicationTests {
    @Autowired
    private TestUserMapper testUserMapper;

    @Test
    void contextLoads() {
        System.out.println(testUserMapper.selectOne("user").getUsername());
        //testUserServer.selectAll().forEach(testUser -> System.out.println(testUser.getName()));
    }
    @Test
    void insert(){
        TestUser testUser = new TestUser();
        testUser.setUsername("admin");
        String password = new BCryptPasswordEncoder().encode("123456");
        testUser.setPassword(password);
        testUser.setAuthority("admin");
        testUser.setCreated(new Date());
        testUser.setUpdated(new Date());
        testUserMapper.insert(testUser);
    }
}
