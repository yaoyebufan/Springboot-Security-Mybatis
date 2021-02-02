package com.example.springsecurity.dao;

import com.example.springsecurity.entity.TestUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestUserMapper {
    List<TestUser> findAll();

    TestUser selectOne(String username);

    void insert(TestUser testUser);

    void update(TestUser testUser);

    void delete(String id);
}
