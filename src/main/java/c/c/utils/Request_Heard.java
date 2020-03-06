package c.c.utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Yukino
 * 2020/3/3
 */
public class Request_Heard {

    //static String cookie="";

    /**
     * 下载视频的 请求头 链接等信息
     * @param flvUrl
     * @param avNuM
     * @param requestMethod
     */
    public static HttpURLConnection requestHeard_downFlv(String flvUrl,String avNuM,String requestMethod)throws Exception{
        URL url = new URL(flvUrl);
        //2, 打开连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", " */*");
        conn.setRequestProperty("Accept-Encoding", " gzip, deflate, br");
        conn.setRequestProperty("Accept-Language", " zh-CN,zh;q=0.9");
        conn.setRequestProperty("Connection", " keep-alive");
        conn.setRequestProperty("Host", " upos-sz-mirrorcos.bilivideo.com");
        conn.setRequestProperty("Referer", "https://www.bilibili.com/video/"+avNuM);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        conn.setRequestProperty("X-Requested-With", " ShockwaveFlash/29.0.0.171");
        conn.setRequestProperty("Cookie", Constant.cookie);
        //3, 设置提交类型
        conn.setRequestMethod(requestMethod);

        //4, 设置允许写出数据,默认是不允许 false
        conn.setDoOutput(true);
        conn.setDoInput(true);//当前的连接可以从服务器读取内容, 默认是true

        Map headers = conn.getHeaderFields();
        Set<String> keys = headers.keySet();
        for( String key : keys ){
            String val = conn.getHeaderField(key);
            System.out.println(key+"    "+val);
        }
        System.out.println( conn.getLastModified() );
        return conn;
    }

    /**
     * json数据等
     * 请求视频地址的请求头
     * @param urlPath
     * @param requistType
     */
    public static HttpURLConnection requestHeard_FlvUrl(String urlPath,String requistType)throws Exception{
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        //conn.setRequestProperty("Accept-Encoding","gzip, deflate, br"); 不选择压缩否则会乱码https://zhuanlan.zhihu.com/p/35643926
        conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
        conn.setRequestProperty("Cache-Control","max-age=0");
        conn.setRequestProperty("Connection","keep-alive");
        conn.setRequestProperty("Host","api.bilibili.com");
        conn.setRequestProperty("Upgrade-Insecure-Requests","1");
        conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        conn.setRequestProperty("Cookie",Constant.cookie);
        conn.setRequestMethod(requistType);
        conn.setDoOutput(true);
        conn.setDoInput(true);//当前的连接可以从服务器读取内容, 默认是true
        return conn;
    }

    public static HttpURLConnection request_UserAgent(String urlPath,String sendMsg,String requistType)throws Exception{
        URL url = new URL(urlPath);
        //2, 打开连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 模拟浏览器请求
        conn.setRequestProperty("User-agent", Constant.userAgent);
        //3, 设置提交类型
        conn.setRequestMethod(requistType);
        //4, 设置允许写出数据,默认是不允许 false
        conn.setDoOutput(true);
        conn.setDoInput(true);//当前的连接可以从服务器读取内容, 默认是true
        if(sendMsg!=null) {
            //5, 获取向服务器写出数据的流
            OutputStream os = conn.getOutputStream();
            //参数是键值队  , 不以"?"开始
            os.write(sendMsg.getBytes());
            os.flush();
        }
        return conn;
    }


    /**
     * 模拟手机浏览器请求
     * @param conn
     * @param avNuM
     */
    public static void Android(HttpURLConnection conn, String avNuM){
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Accept-Encoding", "identity;q=1, *;q=0");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Host", "upos-sz-mirrorks3.bilivideo.com");
            conn.setRequestProperty("Range", "bytes=0-");
            conn.setRequestProperty("Referer", "https://m.bilibili.com/video/"+avNuM);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; en-us; LGMS323 Build/KOT49I.MS32310c) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/69.0.3497.100 Mobile Safari/537.36");
    }

}
