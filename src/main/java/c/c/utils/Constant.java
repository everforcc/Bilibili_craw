package c.c.utils;

import java.io.File;

/**
 * Yukino
 * 2020/3/3
 */
public class Constant {

    /**
     * 设置为自己的文件夹
     */
    public static final String rootFilePath = "E:\\test\\github\\" ;
    static String cookie="";

    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/"+ ToolTime.nowTime("yyyyMMdd")+" Firefox/29.0";
    public static String androidUserAgent = "Mozilla/5.0 (Linux; U; Android 4.4.2; en-us; LGMS323 Build/KOT49I.MS32310c) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/69.0.3497.100 Mobile Safari/537.36";
    public static final String Chrome = "Chrome";
    public static final String Android = "Android";
    public static final String count = "count" ;
    public static final String vlist = "vlist";
    public static final String aid = "aid" ;

    // 地址 s 来代替
    // 复制出来的请求头放到文件里面初始化格式

    //http://i0.hdslb.com/bfs/archive/74af0a6d77214ae7be8f2c11de0079d9004689c9.jpg
    //
    /**
     * 正则表达式中 \\.代表.
     * "" 中 \\ 代表 \
     */
    //private static final String regex = "http://i\\d\\.hdslb\\.com/bfs/archive/\\w*\\.(jpg){0,1}(png){0,1}";
    public static final String regex = "http://i\\d\\.hdslb\\.com/bfs/archive/\\w*\\.[A-Za-z]{3,4}";
    //http://i1.hdslb.com/bfs/archive/e7f1467860cb3a91a2291b815ca9320e2c89ba62.jpg  刀剑直叶
    //http://i2.hdslb.com/bfs/archive/baf3a437486008249cdd0ab4c66ad8612ae7f21e.png  穹妹

    public static String albumCount(){
        return "";
    }

    public static final String dir_video = "视频" ;
    public static final String dir_movie = "电影" ;
    public static final String dir_comic = "电视" ;

    //["高清 1080P+", "高清 1080P", "高清 720P", "清晰 480P", "流畅 360P"] [112, 80, 64, 32, 16]
    public static final String quality_1080_60 = "116" ;
    public static final String quality_1080_up = "112" ;
    public static final String quality_1080 = "80" ;
    public static final String quality_720 = "64" ;
    public static final String quality_480 = "32" ;
    public static final String quality_360 = "16" ;

    //bilibili的视频地址的请求目前都是GET
    public static final String GET = "GET" ;
}
