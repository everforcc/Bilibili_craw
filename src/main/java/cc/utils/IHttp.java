package cc.utils;

import cc.entity.DownMsg;

import java.util.Map;

/**
 * @author everforcc
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

    default void downFile(DownMsg downMsg){

    }

    default void saveFile(DownMsg downMsg){

    }

}
