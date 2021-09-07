package cc.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 郭凯龙
 * @data 2021/9/3 0003
 */
public interface ECode {

    String code();

    String msg();

    default boolean codeExceptionMsg(){
        throw new CodeException(msg());
    }
    default void wrong(){
        isEffect(true);
    }
    /**
     * eg: 正确情况是200，就该写 !"200".equals(msg)
     * @param flag
     */
    default void isEffect(boolean flag){
        if(flag) {
            throw new CodeException(msg());
        }
    }

    default void isEffect(boolean flag,String msg){
        if(flag) {
            throw new CodeException(msg() + msg);
        }
    }

}
