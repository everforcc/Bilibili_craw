package cc.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
public class ConstantHeader {

    private static String COOKIE_VALUE = "";

    public static String cookie = "cookie";
    public static String referer = "referer";
    public static String USER_AGENT = "user-agent";

    private static String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
    private static String ACCEPTJSON = "application/json";
    private static String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36";
    private static String ACCEPT_ENCODING = "gzip, deflate, br";//"gzip, deflate, br"; gzip, 会加速传输，但是会压缩文件
    private static String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.9";
    private static String keepConnection = "keep-alive";
    private static String Host = "upos-sz-mirrorcos.bilivideo.com";

    public static Map<String,String> map = new HashMap<>();
    static {
        map.put("accept",ACCEPT);
        map.put("user-agent", USER_AGENT_VALUE);
        //map.put("accept-encoding",ACCEPT_ENCODING);
        map.put("accept-language",ACCEPT_LANGUAGE);
    }

    public static Map<String,String> mapFlv = new HashMap<>();
    static {
        mapFlv.put("accept",ACCEPT);
        mapFlv.put(USER_AGENT, USER_AGENT_VALUE);
        mapFlv.put("accept-encoding",ACCEPT_ENCODING);
        mapFlv.put("accept-language",ACCEPT_LANGUAGE);
        mapFlv.put("Connection",keepConnection);
        mapFlv.put("Host",Host);
        mapFlv.put("Referer", "https://www.bilibili.com/video/");
        mapFlv.put("X-Requested-With", " ShockwaveFlash/29.0.0.171");
        mapFlv.put(cookie, COOKIE_VALUE);
    }

    public static Map<String,String> web = new HashMap<>();
    static {
        web.put("user-agent", USER_AGENT_VALUE);
    }

    public static Map<String,String> webJSON = new HashMap<>();
    static {
        webJSON.put("Content-Type",ACCEPTJSON);
        webJSON.put("user-agent", USER_AGENT_VALUE);
    }

    public static Map<String,String> webJSONCookie = new HashMap<>();
    static {
        webJSONCookie.put("Content-Type",ACCEPTJSON);
        webJSONCookie.put("ACCEPT",ACCEPT);
        webJSONCookie.put("user-agent", USER_AGENT_VALUE);
        webJSONCookie.put(cookie, COOKIE_VALUE);
        webJSONCookie.put("sec-fetch-dest","document");
        webJSONCookie.put("sec-fetch-mode","navigate");
        webJSONCookie.put("sec-fetch-site","none");
        webJSONCookie.put("sec-fetch-user","?1");
        /**

         sec-fetch-mode: navigate
         sec-fetch-site: none
         sec-fetch-user: ?1
         */
    }

    // "referer", "https://www.bilibili.com/bangumi/play/" + id
    public static Map<String,String> epFlv = new HashMap<>();
    static {
        epFlv.put("Accept", " */*");
        epFlv.put("Accept-Language", " zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        epFlv.put(referer, "https://www.bilibili.com/bangumi/play/");// 需要添加
        epFlv.put("sec-fetch-mode", "no-cors");
        epFlv.put("sec-fetch-site", "cross-site");
        epFlv.put("x-requested-with", "ShockwaveFlash/29.0.0.171");
        epFlv.put(cookie, COOKIE_VALUE);
        epFlv.put("user-agent", USER_AGENT_VALUE);
    }

}
