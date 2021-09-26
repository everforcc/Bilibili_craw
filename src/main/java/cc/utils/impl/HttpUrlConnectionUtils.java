package cc.utils.impl;

import cc.entity.DownMsg;
import cc.utils.IFile;
import cc.utils.IHttp;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
public class HttpUrlConnectionUtils implements IHttp {

    private IFile iFile = new ApacheFileUtils();

    // 处理json
    @Override
    public String get(String urlPath, String type, Map<String, String> map, String... params) {
        StringBuffer content = new StringBuffer("");
        try {

            HttpURLConnection conn = common(urlPath,type,map,params);

            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(),params[0]));
            String str;

            while((str=br.readLine())!=null) {
                content.append(str);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return content.toString();
    }

    @Override
    public void downFile(DownMsg downMsg){
        try {
            HttpURLConnection conn = common(downMsg.getUrl(),downMsg.getType(),downMsg.getHeader(),downMsg.getOtherMsg());
            conn.setDoOutput(true);
            conn.setDoInput(true);//读取数据
            iFile.downFile(conn.getInputStream(),new File(downMsg.getFilePath() + downMsg.getFileName()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private HttpURLConnection common(String urlPath, String type, Map<String, String> map, String... params) throws IOException {
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(type);

        if(map!=null&&map.size()>0){
            for(Map.Entry entry : map.entrySet()){
                String key = (String) entry.getKey();
                if(conn.getRequestProperty(key)==null){
                    String value = (String) entry.getValue();
                    if("Accept-Encoding".equalsIgnoreCase(key)&&value.contains("gzip")){
                        //gzip = true; // 需要解压
                    }
                    // 短视频转发
                    if("Referer".equalsIgnoreCase(key)){
                        //gzip = true; // 需要解压
                        value += params[0];
                    }
                    conn.setRequestProperty(key,value);
                }
            }
        }
        return conn;
    }

}
