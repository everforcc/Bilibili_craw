package cc.constant;

import cc.utils.date.ToolTime;

import java.math.BigDecimal;

/**
 * @author everforcc 2021-10-09
 */
public class ConstantCommon {
    public static String UTF_8 = "UTF-8";
    public static String HTML = ".html";
    public static String JSON = ".json";

    // 10M
    public static BigDecimal MAXIMUM_SIZE = new BigDecimal(10 * 1024);

    /**
     * 设置为自己的文件夹
     */
    public static String cookie="fingerprint=87de439bed5a2212549cf5d4e51c71e9; buvid_fp=20920408-59A5-488E-9D54-3B53E6003C62167644infoc; buvid_fp_plain=20920408-59A5-488E-9D54-3B53E6003C62167644infoc; LIVE_BUVID=AUTO8516337468985782; PVID=1; _uuid=77A98D54-1BBE-9155-C059-5892BF94FD4597774infoc; buvid3=20920408-59A5-488E-9D54-3B53E6003C62167644infoc; CURRENT_BLACKGAP=1; CURRENT_FNVAL=80; CURRENT_QUALITY=0; sid=c7r6xx4g; rpdid=|(kJYkJk)R~|0J'uYJJ~luJ|k; fingerprint=c2937db130cfeea09af0f65820d419de; buvid_fp=20920408-59A5-488E-9D54-3B53E6003C62167644infoc; buvid_fp_plain=CFCF288B-F583-44B8-BAEA-60B745E45342185010infoc; DedeUserID=58572396; DedeUserID__ckMd5=20e54bd3090b9e60; SESSDATA=a6864586%2C1649303960%2C0d3c1*a1; bili_jct=ccf2812adba4582641e0d266cc7831ae";

    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/"+ ToolTime.nowTime("yyyyMMdd")+" Firefox/29.0";
    public static String androidUserAgent = "Mozilla/5.0 (Linux; U; Android 4.4.2; en-us; LGMS323 Build/KOT49I.MS32310c) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/69.0.3497.100 Mobile Safari/537.36";

    //public static final String aid = "aid" ;

    //bilibili的视频地址的请求目前都是GET
    public static final String GET = "GET" ;

}
