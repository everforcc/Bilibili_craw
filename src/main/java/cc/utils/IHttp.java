package cc.utils;

import java.util.Map;

/**
 * @author 郭凯龙
 * @data 2021/9/3 0003
 */
public interface IHttp {

    //

    /**
     * 定义网络请求类型
     * default 只需要实现需要的
     */

    default String get(){
        return null;
    };

    default String get(String urlPath, String type, Map<String,String> map,String... params){
        return null;
    };

    default String post(){
        return null;
    };

}
