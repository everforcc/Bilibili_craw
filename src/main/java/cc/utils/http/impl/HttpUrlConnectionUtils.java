package cc.utils.http.impl;

import cc.constant.ConstantFile;
import cc.entity.DownMsg;
import cc.utils.file.IFileByte;
import cc.utils.file.impl.ApacheFileUtils;
import cc.utils.file.impl.InputStreamUtils;
import cc.utils.http.IHttp;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
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

    private IFileByte iFileByte = new ApacheFileUtils();

    private IFileByte iFileByteStream = new InputStreamUtils();

    // 处理json
    @Override
    public String get(String urlPath, String type, Map<String, String> map, String... params) {
        StringBuffer content = new StringBuffer("");
        try {

            HttpURLConnection conn = common(urlPath, type, map, params);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));//,params[0]
            String str;

            while ((str = br.readLine()) != null) {
                content.append(str);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    @SneakyThrows
    @Override
    public void downFile(DownMsg downMsg) {
        // 用url生成请求头
        HttpURLConnection conn = common(downMsg.getUrl(), downMsg.getReqType(), downMsg.getHeader(), downMsg.getOtherMsg());

        // 获取文件大小
        BigDecimal fileLength = new BigDecimal(conn.getContentLength());
        if (fileLength.compareTo(new BigDecimal(1)) < 1) {
            fileLength = new BigDecimal(conn.getHeaderField("Content-Length"));
        }
        log.info("文件大小: " + fileLength);

        // 按照文件大小选择想在接口
        if (fileLength.compareTo(ConstantFile.MAXIMUM_SIZE) > 0) {
            // 小文件还行，大文件用这种方法，无法处理进度
            log.info("使用接口下载 【iFileStream】");
            iFileByteStream.downFile(conn.getInputStream(), downMsg.getFilePath(), downMsg.getFileName(), fileLength);
        } else {
            log.info("使用接口下载 【iFile】");
            iFileByte.downFile(conn.getInputStream(), downMsg.getFilePath(), downMsg.getFileName());
        }
    }

    @SneakyThrows
    private HttpURLConnection common(String urlPath, String type, Map<String, String> map, String... params) {
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(type);
        conn.setDoOutput(true);
        conn.setDoInput(true);//读取数据

        if (map != null && map.size() > 0) {
            for (Map.Entry entry : map.entrySet()) {
                String key = (String) entry.getKey();
                if (conn.getRequestProperty(key) == null) {
                    String value = (String) entry.getValue();
                    if ("Accept-Encoding".equalsIgnoreCase(key) && value.contains("gzip")) {
                        //gzip = true; // 需要解压
                    }
                    // 短视频转发
                    /*if("Referer".equalsIgnoreCase(key)){
                        //gzip = true; // 需要解压
                        value += params[0];
                    }*/
                    conn.setRequestProperty(key, value);
                }
            }
        }
        return conn;
    }

}
