package cc.constant;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
public class ConstantVideoFlvURL {

    // 视频相关的地址

    /**
     * 网页视频根目录
     */
    public static final String videoUrl = "https://www.bilibili.com/video/av%s";
    public static final String charset = "utf-8";
    public static final String type = "GET";

    /**
     * 封面地址
     */
    public static final String imgXsoupPath = "//meta[12]/@content";
    public static final String pic = "pic";
    /**
     * av，bv互转地址
     */
    public static final String bvidToMsg = "http://api.bilibili.com/x/web-interface/archive/stat?bvid=%s";
    /*
    {"code":0,"message":"0","ttl":1,"data":{"aid":170001,"bvid":"BV17x411w7KC","view":39555310,"danmaku":901153,"reply":168976,"favorite":854743,"coin":254269,"share":586190,"like":757464,"now_rank":0,"his_rank":13,"no_reprint":0,"copyright":2,"argue_msg":"","evaluation":""}}
    */

    /**
     *  av转cid
      */
    public static final String aidToCid = "https://api.bilibili.com/x/web-interface/view?aid=%s";
    public static final String aidToCidType = "GET";

    /**
     *  前面参数固定
     * 参数 avid,cid,qn
     */
    public static final String aidCidToRealVideoUrl = "https://api.bilibili.com/x/player/playurl?fnval=2&otype=json&fnver=0&player=1&avid=%s&cid=%s&qn=%s";
    public static final String aidCidToRealVideoUrlType = "GET";

    public static final String downFileUrlType = "GET";
    public static final String downFileType = ".flv";

    //
    /**
     * 获得720直接播放的地址
     */
    public static final String aidCidToRealVideoUrl_720 = "https://api.bilibili.com/x/player/playurl?fourk=1&platform=html5&high_quality=1&qn=120&aid=%s&bvid=%s&cid=%s";

}
