package com.girl.startup;

import com.girl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 开机启动加载角色-权限列表
 */
@Component
@Order(1)
public class LoadPowerList implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("=================开机启动第一项");
        userService.findPower();

    }
}
