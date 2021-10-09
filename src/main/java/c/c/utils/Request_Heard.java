package c.c.utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Yukino
 * 2020/3/3
 */
public class Request_Heard {

    //  下面的都是请求头等信息不同,有空合并了
    //  range

    // 记录日志
    private static Print_Record println = Print_Record.getInstanse("");

    /**
     * 下载视频的 请求头 链接等信息
     * @param flvUrl
     * @param id
     * @param requestMethod
     */
    public static HttpURLConnection requestHeard_downFlv(String flvUrl,String id,String requestMethod)throws Exception{
        println.println("---"+flvUrl);
        URL url = new URL(flvUrl);
        //2, 打开连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", " */*");
        conn.setRequestProperty("Accept-Encoding", " gzip, deflate, br"); // 视频这个没发现问题就先这样吧
        if(id!=null) {
            if(id.startsWith("av")) {
                conn.setRequestProperty("Accept-Language", " zh-CN,zh;q=0.9");
                conn.setRequestProperty("Connection", " keep-alive");
                conn.setRequestProperty("Host", " upos-sz-mirrorcos.bilivideo.com");
                conn.setRequestProperty("Referer", "https://www.bilibili.com/video/" + id);
            }else if(id.startsWith("ep")||id.startsWith("ss")){
                conn.setRequestProperty("Accept-Language", " zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
                conn.setRequestProperty("referer", "https://www.bilibili.com/bangumi/play/" + id + "?theme=movie");
                conn.setRequestProperty("sec-fetch-mode", "no-cors");
                conn.setRequestProperty("sec-fetch-site", "cross-site");
            }else {
                println.println("id格式异常:" + id);
                throw new Exception("id格式异常");
            }
        }
        conn.setRequestProperty("X-Requested-With", " ShockwaveFlash/29.0.0.171");

        conn = commonProperty(conn);
        return conn;
    }

    /**
     * 下载视频的 请求头 链接等信息
     * @param flvUrl
     * @param flvUrl
     * @param id
     */
    public static HttpURLConnection requestHeard_downEPFlv(String flvUrl,String id)throws Exception{
        println.println("---"+flvUrl);
        URL url = new URL(flvUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", " */*");
        conn.setRequestProperty("Accept-Language", " zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        conn.setRequestProperty("referer", "https://www.bilibili.com/bangumi/play/" + id);// 需要添加
        conn.setRequestProperty("sec-fetch-mode", "no-cors");
        conn.setRequestProperty("sec-fetch-site", "cross-site");
        conn.setRequestProperty("x-requested-with", "ShockwaveFlash/29.0.0.171");

        conn = commonProperty(conn);
        return conn;
    }

    /**
     *  bilibili搜索功能用
     * @param urlPath
     * @param requistType
     */
    public static HttpURLConnection requestHeard_FlvUrl(String urlPath,String requistType)throws Exception{
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
        conn.setRequestProperty("Cache-Control","max-age=0");
        conn.setRequestProperty("Connection","keep-alive");
        conn.setRequestProperty("Host","api.bilibili.com");
        conn.setRequestProperty("Upgrade-Insecure-Requests","1");
        conn = commonProperty(conn);
        return conn;
    }

    private static HttpURLConnection commonProperty(HttpURLConnection conn){
        /**
         * 1. conn.setRequestProperty("Accept-Encoding", " gzip, deflate, br"); //加了这个会莫名其妙的乱码 注意存下来
         * 不选择压缩否则会乱码https://zhuanlan.zhihu.com/p/35643926
         */
        conn.setRequestProperty("User-Agent",Constant.userAgent);
        conn.setRequestProperty("Cookie",Constant.cookie);
        try {
            conn.setRequestMethod(Constant.GET); //全都是get
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        conn.setDoOutput(true);
        conn.setDoInput(true);//当前的连接可以从服务器读取内容, 默认是true

        Map headers = conn.getHeaderFields();
        Set<String> keys = headers.keySet();
        for( String key : keys ){
            String val = conn.getHeaderField(key);
            println.println(key+"    "+val);
        }
        //println.println("上次修改时间:" + ToolTime.nowTime(conn.getLastModified()));
        return conn;
    }

    public static HttpURLConnection request_UserAgent(String urlPath,String sendMsg,String requistType)throws Exception{
        URL url = new URL(urlPath);
        //2, 打开连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 模拟浏览器请求
        conn.setRequestProperty("User-agent", Constant.userAgent);
        conn.setRequestProperty("cookie", Constant.cookie);
        //3, 设置提交类型
        conn.setRequestMethod(requistType);
        //4, 设置允许写出数据,默认是不允许 false
        conn.setDoOutput(true);
        conn.setDoInput(true);//当前的连接可以从服务器读取内容, 默认是true

        // 这个位置可以设置分批下载
        // conn.setRequestProperty("Range", "bytes=0-10000000");

        if(sendMsg!=null) {
            //5, 获取向服务器写出数据的流
            OutputStream os = conn.getOutputStream();
            //参数是键值队  , 不以"?"开始
            os.write(sendMsg.getBytes());
            os.flush();
        }
        return conn;
    }

}
