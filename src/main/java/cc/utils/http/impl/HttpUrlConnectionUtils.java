package cc.utils.http.impl;

import cc.constant.ConstantCommon;
import cc.entity.DownMsg;
import cc.utils.file.IFile;
import cc.utils.http.IHttp;
import cc.utils.file.impl.ApacheFileUtils;
import cc.utils.file.impl.InputStreamUtils;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
@Slf4j
public class HttpUrlConnectionUtils implements IHttp {

    private IFile iFile = new ApacheFileUtils();

    private IFile iFileStream = new InputStreamUtils();

    // 处理json
    @Override
    public String get(String urlPath, String type, Map<String, String> map, String... params) {
        StringBuffer content = new StringBuffer("");
        try {

            HttpURLConnection conn = common(urlPath,type,map,params);

            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));//,params[0]
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

            BigDecimal fileLength = new BigDecimal(conn.getContentLength());
            if(fileLength.compareTo(new BigDecimal(1))<1) {
                fileLength = new BigDecimal(conn.getHeaderField("Content-Length"));
            }

            log.info("文件大小: " + fileLength);
            //小文件还行，大文件用这种方法，无法处理进度
            if(fileLength.compareTo(ConstantCommon.MAXIMUM_SIZE)>0){
                iFileStream.downFile(conn.getInputStream(),downMsg.getFilePath(),downMsg.getFileName(),fileLength);
            }else {
                iFile.downFile(conn.getInputStream(),downMsg.getFilePath(),downMsg.getFileName());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveFile(DownMsg downMsg){
        try {
            iFile.saveStrToFile(downMsg.getContent(),new File(downMsg.getFilePath() + downMsg.getFileName()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String readFile(DownMsg downMsg){
        try {
            return iFile.readStrToFile(downMsg.getContent(),new File(downMsg.getFilePath() + downMsg.getFileName()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
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
                    /*if("Referer".equalsIgnoreCase(key)){
                        //gzip = true; // 需要解压
                        value += params[0];
                    }*/
                    conn.setRequestProperty(key,value);
                }
            }
        }
        return conn;
    }

}
