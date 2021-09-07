package cc.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 郭凯龙
 * @data 2021/9/3 0003
 */
public class ConstantHeader {


    private static String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
    private static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36";
    private static String ACCEPT_ENCODING = "gzip, deflate, br";//"gzip, deflate, br"; gzip, 会加速传输，但是会压缩文件
    private static String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.9";
    private static String COOKIE = "";
    private static String keepConnection = "keep-alive";
    private static String Host = "upos-sz-mirrorcos.bilivideo.com";

    public static Map<String,String> map = new HashMap<>();
    static {
        map.put("accept",ACCEPT);
        map.put("user-agent",USER_AGENT);
        //map.put("accept-encoding",ACCEPT_ENCODING);
        map.put("accept-language",ACCEPT_LANGUAGE);
    }

    public static Map<String,String> mapFlv = new HashMap<>();
    static {
        mapFlv.put("accept",ACCEPT);
        mapFlv.put("user-agent",USER_AGENT);
        mapFlv.put("accept-encoding",ACCEPT_ENCODING);
        mapFlv.put("accept-language",ACCEPT_LANGUAGE);
        mapFlv.put("Connection",keepConnection);
        mapFlv.put("Host",Host);
        mapFlv.put("Referer", "https://www.bilibili.com/video/");
        mapFlv.put("X-Requested-With", " ShockwaveFlash/29.0.0.171");
        mapFlv.put("cookie",COOKIE);
    }

    public static Map<String,String> web = new HashMap<>();
    static {
        web.put("user-agent",USER_AGENT);
    }

}
