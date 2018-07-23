package com.girl.service;

import com.girl.dao.UserDao;
import com.girl.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Created by a1668 on 2018/7/11.
 */
@Service
public class UserService {


    @Resource
    private UserDao userDao;

    //查询角色权限列表
    public void findPower(){
        System.out.println("======开机启动查询角色权限列表Service");
    }


    //查询所有
    public Iterable<User> findListUser(Sort sort){

        return userDao.findAll(sort);
    }

    //分页查询
    public Page<User> findPageList(Pageable pageable){

        return userDao.findAll(pageable);

    }

    //查询一条
    public Optional<User> findById(Integer id){

        return Optional.ofNullable(userDao.findOne(id));
    }

    //新增
    public User save(User user){
        return userDao.save(user);
    }


    //判断是否存在
    public boolean  existsById(Integer id){

        return userDao.exists(id);
    }


    //删除
    public void deleteById(Integer id){

        userDao.delete(id);
    }



}
