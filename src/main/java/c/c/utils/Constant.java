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
    public static final String rootFilePath = "E:" + File.separator + "test" + File.separator + "github" + File.separator ;
    static String cookie="l=v; _uuid=8B9D4BFE-64EF-B8B6-E901-BC64AA36FA6B81905infoc; buvid3=34DEA7B4-4819-4329-841A-F1D908941268138392infoc; sid=6bgvpmoi; DedeUserID=58572396; DedeUserID__ckMd5=20e54bd3090b9e60; SESSDATA=40c8ae33%2C1616331012%2Caa953*91; bili_jct=d9767064501a2b1a310c59e20f1e3721; CURRENT_FNVAL=80; blackside_state=1; rpdid=|(J|)J|uullJ0J'uY||~~m|Ru; bsource=search_baidu; bp_video_offset_58572396=438132817916329216; bp_t_offset_58572396=438132817916329216";

    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/"+ ToolTime.nowTime("yyyyMMdd")+" Firefox/29.0";
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

    //["高清 1080P+", "高清 1080P", "高清 720P", "清晰 480P", "流畅 360P"] [112, 80, 64, 32, 16]
    public static final String quality_1080_up = "112" ;
    public static final String quality_1080 = "80" ;
    public static final String quality_720 = "64" ;
    public static final String quality_480 = "32" ;
    public static final String quality_360 = "16" ;

}
