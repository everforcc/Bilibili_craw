package cc.utils.http.impl;

import cc.constant.ConstantHeader;
import cc.utils.http.IHttp;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
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

        if(map.containsKey(ConstantHeader.COOKIE_KEY)){
            // jsoup的cookie必须这样设置
            connection.cookie(ConstantHeader.COOKIE_KEY,map.get(ConstantHeader.COOKIE_KEY));
        }

        if(map.containsKey(ConstantHeader.USER_AGENT_KEY)){
            // jsoup的cookie必须这样设置
            connection.userAgent(map.get(ConstantHeader.USER_AGENT_KEY));
        }

        if(map!=null&&map.size()>0){
            for(Map.Entry entry : map.entrySet()){
                String key = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    if("Accept-Encoding".equalsIgnoreCase(key)&&value.contains("gzip")){
                        //gzip = true; // 需要解压
                    }
                    // 短视频转发
                    if("referer".equalsIgnoreCase(key)){
                        //gzip = true; // 需要解压
                        value += params[0];
                    }
                connection.header(key,value);
            }
        }
        //connection.data(map);
        Connection.Response login = null;// 获取响应
        try {
            login = connection.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return login.body();
    };

}
