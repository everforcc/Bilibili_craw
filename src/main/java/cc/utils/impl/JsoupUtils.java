package cc.utils.impl;

import c.c.utils.Constant;
import cc.utils.IHttp;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

/**
 * @author everforcc 2021-09-07
 */
public class JsoupUtils implements IHttp {

    // 请求forhtml
    public String get(String urlPath, String type, Map<String,String> map, String... params){
        Connection.Method method = Connection.Method.GET;
        if("POST".equals(type)){
            method = Connection.Method.POST;
        }

        Connection connection = Jsoup.connect(urlPath).method(method).ignoreContentType(true);// 获取连接,不检查格式

        if(map.containsKey("cookie")){
            // jsoup的cookie必须这样设置
            connection.cookie("cookie",map.get("cookie"));
        }

        connection.data(map);
        Connection.Response login = null;// 获取响应
        try {
            login = connection.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return login.body();
    };

}
