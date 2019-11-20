package com.example.sharding.service;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.example.sharding.domain.entity.UserInfo;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wf
 * @date 2019/11/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceTest {

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void addUserInfo() {
        UserInfo userInfo = UserInfo.builder().id(1111L).userName("ZhangSan").password("123456").account(100).build();

        userInfoService.addUserInfo(userInfo);
    }

    @Test
    public void getUserInfoList() {
        userInfoService.getUserInfoList();

        System.out.println("userInfoList:"+userInfoService.getUserInfoList());
    }

    @Test
    public void findUserById() {
        System.out.println("findUserById:"+userInfoService.findUserById(1111L));
    }
}