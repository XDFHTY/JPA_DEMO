package com.girl.exception;

import com.girl.enums.ResultEnums;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 朱文赵
 * 2017/9/26
 */
public class LibraryException extends RuntimeException {

    @Getter @Setter
    private Integer code;

    /** 构造函数*/
    public LibraryException(ResultEnums resultEnums){
        super(resultEnums.getMsg());
        this.code = resultEnums.getCode();
    }

}
