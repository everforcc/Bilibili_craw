package cc.utils.impl;

import cc.utils.IHttp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @author 郭凯龙
 * @data 2021/9/3 0003
 */
public class HttpUrlConnectionUtils implements IHttp {

    @Override
    public String get(String urlPath, String type, Map<String, String> map, String... params) {
        StringBuffer content = new StringBuffer("");
        try {

            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if(map!=null&&map.size()>0){
                for(Map.Entry entry : map.entrySet()){
                    String key = (String) entry.getKey();
                    if(conn.getRequestProperty(key)==null){
                        String value = (String) entry.getValue();
                        conn.setRequestProperty(key,value);
                        if("Accept-Encoding".equalsIgnoreCase(key)&&value.contains("gzip")){
                            //gzip = true; // 需要解压
                        }
                    }
                }
            }
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String str;

            while((str=br.readLine())!=null) {
                content.append(str);
            }

        }catch (Exception e){

        }

        return content.toString();
    }
}
