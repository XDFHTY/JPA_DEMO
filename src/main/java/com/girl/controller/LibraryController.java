package com.girl.controller;

import com.girl.enums.ResultEnums;
import com.girl.form.Novel;
import com.girl.service.LibraryService;
import com.girl.utils.ResultUtils;
import com.girl.vo.Result;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 朱文赵
 * 2017/9/26
 */
@RestController
@RequestMapping("/book/novel")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    /**
     * 查询方法
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result get(@PathVariable("id") String id){
        return ResultUtils.success(libraryService.get(id));
    }

    /**
     * 添加方法
     * @param novel
     * @return
     */
    @PostMapping("/add")
    public Result add(@Valid Novel novel, BindingResult bindingResult){
        List<String> msgList = new ArrayList<>();
        if(bindingResult.hasErrors()){
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                msgList.add(fieldError.getDefaultMessage());
            }
            return ResultUtils.error(ResultEnums.FORM_VALIDATION_ERROR, msgList);
        }
        return ResultUtils.success(libraryService.add(novel));
    }

    /**
     * 删除方法
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") String id){
        libraryService.delete(id);
        return ResultUtils.success();
    }

    /**
     * 更新方法
     * @param id
     * @param novel
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestParam("id") String id, @Valid Novel novel,
                         BindingResult bindingResult){
        List<String> msgList = new ArrayList<>();
        if(bindingResult.hasErrors()){
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                msgList.add(fieldError.getDefaultMessage());
            }
            return ResultUtils.error(ResultEnums.FORM_VALIDATION_ERROR, msgList);
        }
        libraryService.update(id, novel);
        return ResultUtils.success(libraryService.get(id));
    }



    @PostMapping("/addMapping")
    public void addMapping() throws Exception {
        libraryService.CreateIndexAndMapping();
    }

}
