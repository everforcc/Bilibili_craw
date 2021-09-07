package cc.utils.impl;

import c.c.utils.Constant;
import cc.utils.IHttp;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

/**
 * @author guokailong 2021-09-07
 */
public class JsoupUtils implements IHttp {

    // 请求forhtml
    public String get(String urlPath, String type, Map<String,String> map, String... params){

        Connection connection = Jsoup.connect(urlPath);// 获取连接
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
