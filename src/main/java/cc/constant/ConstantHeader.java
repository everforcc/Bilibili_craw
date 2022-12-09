package cc.constant;

import cc.utils.date.ToolTime;

import java.util.HashMap;
import java.util.Map;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
public class ConstantHeader {

    /* 个人的cookie值 */
    public static final String COOKIE_VALUE = "_uuid=7F299FD1-511E-6A83-6109B-C13CB28D8EAA96521infoc; buvid3=901FE39C-1743-973E-9F92-0D7CFD1E9BD099012infoc; b_nut=1669550496; buvid4=4CF50B5E-17ED-4EF4-E326-6151509491E599012-022112720-Ei5K3XdcNIilHaqCC5VRFw%3D%3D; fingerprint=1636b3cbf3f243fed486b0e2d8039a6f; buvid_fp_plain=undefined; SESSDATA=b189f8ae%2C1685148686%2C0a77e%2Ab2; bili_jct=2bf50a1ace6a39bc206a893887ff6519; DedeUserID=58572396; DedeUserID__ckMd5=20e54bd3090b9e60; hit-new-style-dyn=0; hit-dyn-v2=1; CURRENT_FNVAL=4048; rpdid=|(YYRmY)YRu0J'uYYmlmY)J|; blackside_state=1; nostalgia_conf=-1; b_ut=5; LIVE_BUVID=AUTO1116696305735860; CURRENT_QUALITY=80; i-wanna-go-feeds=-1; i-wanna-go-back=2; buvid_fp=1636b3cbf3f243fed486b0e2d8039a6f; bp_video_offset_58572396=737475215243608000; b_lsid=51710CB8C_184F4A44CC0; sid=70b490cr; bsource=search_baidu; PVID=2";

    public static final String COOKIE_KEY = "cookie";
    public static final String REFERER = "referer";


    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
    private static final String ACCEPTJSON = "application/json";

    public static final String USER_AGENT_KEY = "user-agent";
    public static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/"+ ToolTime.nowTime("yyyyMMdd")+" Firefox/29.0";
    public static final String USER_AGENT_andro_VALUE = "Mozilla/5.0 (Linux; U; Android 4.4.2; en-us; LGMS323 Build/KOT49I.MS32310c) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/69.0.3497.100 Mobile Safari/537.36";

    private static final String ACCEPT_ENCODING = "gzip, deflate, br";//"gzip, deflate, br"; gzip, 会加速传输，但是会压缩文件
    private static final String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.9";
    private static final String keepConnection = "keep-alive";
    private static final String Host = "upos-sz-mirrorcos.bilivideo.com";

    public static Map<String, String> map = new HashMap<>();

    static {
        map.put("accept", ACCEPT);
        map.put("user-agent", USER_AGENT_VALUE);
        //map.put("accept-encoding",ACCEPT_ENCODING);
        map.put("accept-language", ACCEPT_LANGUAGE);
        map.put(COOKIE_KEY, COOKIE_VALUE);
    }

    public static Map<String, String> mapFlv = new HashMap<>();

    static {
        mapFlv.put("accept", ACCEPT);
        mapFlv.put(USER_AGENT_KEY, USER_AGENT_VALUE);
        mapFlv.put("accept-encoding", ACCEPT_ENCODING);
        mapFlv.put("accept-language", ACCEPT_LANGUAGE);
        mapFlv.put("Connection", keepConnection);
        mapFlv.put("Host", Host);
        mapFlv.put("Referer", "https://www.bilibili.com/video/");
        mapFlv.put("X-Requested-With", " ShockwaveFlash/29.0.0.171");
        mapFlv.put(COOKIE_KEY, COOKIE_VALUE);
    }

    public static Map<String, String> web = new HashMap<>();

    static {
        web.put("user-agent", USER_AGENT_VALUE);
    }

    public static Map<String, String> webJSON = new HashMap<>();

    static {
        webJSON.put("Content-Type", ACCEPTJSON);
        webJSON.put("user-agent", USER_AGENT_VALUE);
    }

    public static Map<String, String> webJSONCookie = new HashMap<>();

    static {
        webJSONCookie.put("Content-Type", ACCEPTJSON);
        webJSONCookie.put("ACCEPT", ACCEPT);
        webJSONCookie.put("user-agent", USER_AGENT_VALUE);
        webJSONCookie.put(COOKIE_KEY, COOKIE_VALUE);
        webJSONCookie.put("sec-fetch-dest", "document");
        webJSONCookie.put("sec-fetch-mode", "navigate");
        webJSONCookie.put("sec-fetch-site", "none");
        webJSONCookie.put("sec-fetch-user", "?1");
        /**

         sec-fetch-mode: navigate
         sec-fetch-site: none
         sec-fetch-user: ?1
         */
    }

    // "referer", "https://www.bilibili.com/bangumi/play/" + id
    public static Map<String, String> epFlv = new HashMap<>();

    static {
        epFlv.put("Accept", " */*");
        epFlv.put("Accept-Language", " zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        epFlv.put(REFERER, "https://www.bilibili.com/bangumi/play/");// 需要添加
        epFlv.put("sec-fetch-mode", "no-cors");
        epFlv.put("sec-fetch-site", "cross-site");
        epFlv.put("x-requested-with", "ShockwaveFlash/29.0.0.171");
        epFlv.put(COOKIE_KEY, COOKIE_VALUE);
        epFlv.put("user-agent", USER_AGENT_VALUE);
    }

}
