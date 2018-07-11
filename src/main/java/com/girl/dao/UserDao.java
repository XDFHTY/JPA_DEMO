package com.girl.dao;

import com.girl.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by a1668 on 2018/7/11.
 */
public interface UserDao  extends PagingAndSortingRepository<User,Integer>  {
}
