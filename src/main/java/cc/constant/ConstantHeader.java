package cc.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
public class ConstantHeader {


    private static String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
    private static String ACCEPTJSON = "application/json";
    private static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36";
    private static String ACCEPT_ENCODING = "gzip, deflate, br";//"gzip, deflate, br"; gzip, 会加速传输，但是会压缩文件
    private static String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.9";
    private static String COOKIE = "fingerprint=87de439bed5a2212549cf5d4e51c71e9; buvid_fp=20920408-59A5-488E-9D54-3B53E6003C62167644infoc; buvid_fp_plain=20920408-59A5-488E-9D54-3B53E6003C62167644infoc; LIVE_BUVID=AUTO8516337468985782; PVID=1; _uuid=77A98D54-1BBE-9155-C059-5892BF94FD4597774infoc; buvid3=20920408-59A5-488E-9D54-3B53E6003C62167644infoc; CURRENT_BLACKGAP=1; CURRENT_FNVAL=80; CURRENT_QUALITY=0; sid=c7r6xx4g; rpdid=|(kJYkJk)R~|0J'uYJJ~luJ|k; fingerprint=c2937db130cfeea09af0f65820d419de; buvid_fp=20920408-59A5-488E-9D54-3B53E6003C62167644infoc; buvid_fp_plain=CFCF288B-F583-44B8-BAEA-60B745E45342185010infoc; DedeUserID=58572396; DedeUserID__ckMd5=20e54bd3090b9e60; SESSDATA=a6864586%2C1649303960%2C0d3c1*a1; bili_jct=ccf2812adba4582641e0d266cc7831ae";
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

    public static Map<String,String> webJSON = new HashMap<>();
    static {
        webJSON.put("Content-Type",ACCEPTJSON);
        webJSON.put("user-agent",USER_AGENT);
    }

    public static Map<String,String> webJSONCookie = new HashMap<>();
    static {
        webJSONCookie.put("Content-Type",ACCEPTJSON);
        webJSONCookie.put("ACCEPT",ACCEPT);
        webJSONCookie.put("user-agent",USER_AGENT);
        webJSONCookie.put("cookie",COOKIE);
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
        epFlv.put("referer", "https://www.bilibili.com/bangumi/play/ss26175");// 需要添加
        epFlv.put("sec-fetch-mode", "no-cors");
        epFlv.put("sec-fetch-site", "cross-site");
        epFlv.put("x-requested-with", "ShockwaveFlash/29.0.0.171");
    }

}
