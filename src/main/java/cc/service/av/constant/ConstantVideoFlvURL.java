package cc.service.av.constant;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
public class ConstantVideoFlvURL {

    // 视频相关的地址

    /**
     * 1. aid格式化视频地址
     */
    public static final String videoUrl = "https://www.bilibili.com/video/av%s";

    /**
     * 2. 封面地址
     */
    public static final String imgXsoupPath = "//meta[12]/@content";
    public static final String pic = "pic";

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

    /**
     * 3. av，bv互转地址
     */
    public static final String bvidToMsg = "http://api.bilibili.com/x/web-interface/archive/stat?bvid=%s";
    /*
    {"code":0,"message":"0","ttl":1,"data":{"aid":170001,"bvid":"BV17x411w7KC","view":39555310,"danmaku":901153,"reply":168976,"favorite":854743,"coin":254269,"share":586190,"like":757464,"now_rank":0,"his_rank":13,"no_reprint":0,"copyright":2,"argue_msg":"","evaluation":""}}
    */

    /**
     * 4. av转cid
     */
    public static final String aidToCid = "https://api.bilibili.com/x/web-interface/view?aid=%s";

    /**
     * 5. 用aid和cid获取真实视频地址
     * 参数 avid,cid,qn
     */
    public static final String aidCidToRealVideoUrl = "https://api.bilibili.com/x/player/playurl?fnval=2&otype=json&fnver=0&player=1&avid=%s&cid=%s&qn=%s";

    /**
     * 6. 获得720直接播放的地址
     */
    public static final String aidCidToRealVideoUrl_720 = "https://api.bilibili.com/x/player/playurl?fourk=1&platform=html5&high_quality=1&qn=120&aid=%s&bvid=%s&cid=%s";

}
