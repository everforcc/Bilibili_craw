/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 10:57
 * Copyright
 */

package cc.busi.config;

import cc.constant.ConstantHeader;
import cc.constant.ConstantReqType;
import cc.entity.DownMsg;
import cc.utils.date.ToolTime;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Bilibili_Heard {

    /**
     * 下载视频的 请求头 链接等信息
     * <p>
     * flvUrl  请求地址
     *
     * @param downMsg 文件信息
     */

    @SneakyThrows
    public static HttpURLConnection requestHeard_downFlv(DownMsg downMsg) {
        String flvUrl = downMsg.getUrl();
        log.info("---" + flvUrl);
        String id = downMsg.getAid();
        String requestMethod = downMsg.getReqType();
        URL url = new URL(flvUrl);
        //2, 打开连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", " */*");
        conn.setRequestProperty("Accept-Encoding", " gzip, deflate, br"); // 视频这个没发现问题就先这样吧
        if (id != null) {
            if (id.startsWith("av")) {
                conn.setRequestProperty("Accept-Language", " zh-CN,zh;q=0.9");
                conn.setRequestProperty("Connection", " keep-alive");
                conn.setRequestProperty("Host", " upos-sz-mirrorcos.bilivideo.com");
                conn.setRequestProperty("Referer", "https://www.bilibili.com/video/" + id);
            } else if (id.startsWith("ep") || id.startsWith("ss")) {
                conn.setRequestProperty("Accept-Language", " zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
                // ?from_spmid=666.25.episode.0
                // ?theme=movie
                conn.setRequestProperty("referer", "https://www.bilibili.com/bangumi/play/" + id + "?from_spmid=666.25.episode.0");
                conn.setRequestProperty("sec-fetch-mode", "no-cors");
                conn.setRequestProperty("sec-fetch-site", "cross-site");
            } else {
                log.info("id格式异常:" + id);
                throw new Exception("id格式异常");
            }
        }
        conn.setRequestProperty("X-Requested-With", " ShockwaveFlash/29.0.0.171");
        conn.setRequestMethod(requestMethod);
        commonProperty(conn);
        return conn;
    }

    /**
     * 下载视频的 请求头 链接等信息
     *
     * @param flvUrl 请求地址
     * @param id     番号id
     */
    @SneakyThrows
    public static HttpURLConnection requestHeard_downEPFlv(String flvUrl, String id) {
        log.info("---" + flvUrl);
        URL url = new URL(flvUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", " */*");
        conn.setRequestProperty("Accept-Language", " zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        conn.setRequestProperty("referer", "https://www.bilibili.com/bangumi/play/" + id);// 需要添加
        conn.setRequestProperty("sec-fetch-mode", "no-cors");
        conn.setRequestProperty("sec-fetch-site", "cross-site");
        conn.setRequestProperty("x-requested-with", "ShockwaveFlash/29.0.0.171");

        commonProperty(conn);
        return conn;
    }

    /**
     * bilibili搜索功能用
     *
     * @param urlPath       请求地址
     * @param requestMethod 请求方式
     */
    public static HttpURLConnection requestHeard_FlvUrl(String urlPath, String requestMethod) throws Exception {
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
        conn.setRequestProperty("Cache-Control", "max-age=0");
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Host", "api.bilibili.com");
        conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
        conn.setRequestMethod(requestMethod);
        commonProperty(conn);
        return conn;
    }

    /**
     * 公共的请求配置
     *
     * @param conn 请求客户端
     */
    private static void commonProperty(HttpURLConnection conn) {

        // 1. conn.setRequestProperty("Accept-Encoding", " gzip, deflate, br"); //加了这个会莫名其妙的乱码 注意存下来
        // 2. 不选择压缩否则会乱码https://zhuanlan.zhihu.com/p/35643926
        conn.setRequestProperty(ConstantHeader.USER_AGENT_KEY, ConstantHeader.USER_AGENT_VALUE);
        conn.setRequestProperty(ConstantHeader.COOKIE_KEY, ConstantHeader.COOKIE_VALUE);
        try {
            conn.setRequestMethod(ConstantReqType.GET); //全都是get
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        conn.setDoOutput(true);
        conn.setDoInput(true);//当前的连接可以从服务器读取内容, 默认是true

        // 打印请求头
        Map<String, List<String>> headers = conn.getHeaderFields();
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            String val = conn.getHeaderField(key);
            log.debug(key + "    " + val);
        }
        log.info("上次修改时间:" + ToolTime.nowTime(conn.getLastModified()));
    }

    /**
     * 简单的 请求,发送请求信息
     *
     * @param urlPath       请求路径
     * @param sendMsg       发送信息
     * @param requestMethod 请求方式
     * @return 返回客户端
     * @throws Exception 抛出的异常
     */
    public static HttpURLConnection request_UserAgent(String urlPath, String sendMsg, String requestMethod) throws Exception {
        URL url = new URL(urlPath);
        //2, 打开连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 模拟浏览器请求
        conn.setRequestProperty(ConstantHeader.USER_AGENT_KEY, ConstantHeader.USER_AGENT_VALUE);
        conn.setRequestProperty(ConstantHeader.COOKIE_KEY, ConstantHeader.COOKIE_VALUE);
        //3, 设置提交类型
        conn.setRequestMethod(requestMethod);
        //4, 设置允许写出数据,默认是不允许 false
        conn.setDoOutput(true);
        conn.setDoInput(true);//当前的连接可以从服务器读取内容, 默认是true

        // 这个位置可以设置分批下载
        // conn.setRequestProperty("Range", "bytes=0-10000000");

        if (sendMsg != null) {
            //5, 获取向服务器写出数据的流
            OutputStream os = conn.getOutputStream();
            //参数是键值队  , 不以"?"开始
            os.write(sendMsg.getBytes());
            os.flush();
        }
        return conn;
    }


}
