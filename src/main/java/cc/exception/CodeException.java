package cc.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 郭凯龙
 * @data 2021/9/3 0003
 */
@Slf4j
public class CodeException extends RuntimeException {

    //
    public CodeException(String msg){
        super(msg);
        log.error(msg);
    }

    public void toException(String msg){


    }

}
