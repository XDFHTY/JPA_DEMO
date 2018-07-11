package com.girl.controller;

/**
 * Created by a1668 on 2018/7/11.
 */

import com.girl.entity.User;
import com.girl.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 获取用户列表数据
     * @return
     */
    @RequestMapping("/findList")
    public Iterable<User> findList(){
        // 指定排序参数对象：根据id，进行降序查询
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort sort = new Sort(order);
        return userService.findListUser(sort);
    }

    /**
     * 分页查询
     * @param pageIndex 第n页
     * @return
     */
    @RequestMapping("/findPageList")
    public List<User> findPageList(int pageIndex){
        // 指定排序参数对象：根据id，进行降序查询
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort sort = new Sort(order);
        /**
         * 封装分页实体
         * 参数一：pageIndex表示当前查询的第几页(默认从0开始，0表示第一页)
         * 参数二：表示每页展示多少数据，现在设置每页展示2条数据
         * 参数三：封装排序对象，根据该对象的参数指定根据id降序查询
         */
        Pageable page = new PageRequest(pageIndex, 2, sort);
        Page<User> articleDatas = userService.findPageList(page);
        System.out.println("查询总页数:" + articleDatas.getTotalPages());
        System.out.println("查询总记录数:" + articleDatas.getTotalElements());
        System.out.println("查询当前第几页:" + articleDatas.getNumber() + 1);
        System.out.println("查询当前页面的记录数:" + articleDatas.getNumberOfElements());
        // 查询出的结果数据集合
        List<User> articles = articleDatas.getContent();
        System.out.println("查询当前页面的集合:" + articles);
        return articles;
    }

    /**
     * 单条数据
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public User findById(Integer id){
        return userService.findById(id).get();
    }

    /**
     * 新增
     * @param id
     * @return
     */
    @GetMapping("/save")
    public User save(User user){
        return userService.save(user);
    }

    /**
     * 判断是否存在
     * @param id
     * @return
     */
    @RequestMapping("/existsById")
    public boolean existsById(Integer id){
        return  userService.existsById(id);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    public void deleteById(Integer id){
        userService.deleteById(id);
    }

}