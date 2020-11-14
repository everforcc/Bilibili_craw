package c.c.utils;

public class ConstantURL {

    public static String quality = "";

    public static String allFlvUrl(String mid,int ps,int pn){
        // pn 页码 mid up id  ps pagesize页面尺寸
        return "https://api.bilibili.com/x/space/arc/search?tid=0&keyword=&order=pubdate&jsonp=jsonp&pn="+pn+"&ps="+ps+"&mid="+mid;
    }

    /**
     *  获取视频对应的cid
     * @param aid
     * @return
     */
    public static String getCidUrl(String aid){
        return "https://api.bilibili.com/x/web-interface/view?aid="+aid;
    }

    /**
     * 获取具体视频的url
     * @param avid
     * @param cid
     * @return
     */
    public static String getFlvUrl(String avid,String cid){
        // 短视频
        //cid: 152777757
        //avid: 89453968
        //qn: 80  视频质量
        //otype: json
        //      "accept_format": "hdflv2,flv,flv720,flv480,mp4",
        //		"accept_description": ["高清 1080P+", "高清 1080P", "高清 720P", "清晰 480P", "流畅 360P"],
        //		"accept_quality": [112, 80, 64, 32, 16],
        //requestFrom: bilibili-helper
        return "https://api.bilibili.com/x/player/playurl?fnval=2&otype=json&avid="+avid+"&fnver=0&qn="+quality+"&player=1&cid="+cid;
    }

    /**
     *  可以获得视频的真实链接 电影和电视
     * @param ep
     * @param aid
     * @param bvid
     * @param cid
     * @return
     */
    public static String epUrlPlayurl(String ep,String aid,String bvid,String cid){
        return "https://api.bilibili.com/pgc/player/web/playurl?ep%5Fid=" + ep
                + "&avid=" + aid + "&fnver=0&player=1&bvid=" + bvid + "&otype=json&qn=" + quality + "&fnval=0&cid=" + cid + "";
    }

    /**
     *  获取下载所需的信息
     * @param id
     * @return
     */
    public static String epUrlPlay(String id){
        return  "https://www.bilibili.com/bangumi/play/" + id + "";
    }

}
