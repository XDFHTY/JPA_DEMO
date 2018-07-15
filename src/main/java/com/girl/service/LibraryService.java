package com.girl.service;


import com.girl.form.Novel;

import java.util.Map;

/**
 * 图书馆服务类
 * Created by 朱文赵
 * 2017/9/26
 */
public interface LibraryService {

    Map<String, Object> get(String id);

    Map<String, Object> add(Novel novel);

    void delete(String id);

    void update(String id, Novel novel);

}
