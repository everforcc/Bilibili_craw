package c.c.utils;

/**
 * Yukino
 * 2020/3/3
 */
public class Constant {

    /**
     * 设置为自己的文件夹
     */
    public static final String rootFilePath = "D:\\test\\github\\";
    static String cookie="_uuid=B12C70F0-DCA2-46BC-D28D-8C8E1FB3A17324567infoc; buvid3=64E2389B-F33E-4BFB-997E-EC791DBE770353923infoc; sid=hxyu20pz; CURRENT_FNVAL=16; stardustvideo=1; LIVE_BUVID=AUTO4315775147642650; rpdid=|(J|)J|ukluk0J'ul~~luR~YY; CURRENT_QUALITY=80; im_notify_type_58572396=0; Hm_lvt_8a6e55dbd2870f0f5bc9194cddf32a02=1580288228,1581048406,1581840591,1581840703; DedeUserID=58572396; DedeUserID__ckMd5=20e54bd3090b9e60; SESSDATA=b01aedd7%2C1585875470%2C2909b331; bili_jct=31f7aadfbfed82ad88d94336e6851885; flash_player_gray=false; bp_t_offset_58572396=364260235120844938; INTVER=1";


    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/"+ ToolTime.nowTime("yyyyMMdd")+" Firefox/29.0";
    public static final String Chrome = "Chrome";
    public static final String Android = "Android";
    public static final String count = "count" ;
    public static final String vlist = "vlist";
    public static final String aid = "aid" ;

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


}
