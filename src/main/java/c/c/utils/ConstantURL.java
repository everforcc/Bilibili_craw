package c.c.utils;

public class ConstantURL {

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
        //cid: 152777757
        //avid: 89453968
        //qn: 80  视频质量
        //otype: json
        //      "accept_format": "hdflv2,flv,flv720,flv480,mp4",
        //		"accept_description": ["高清 1080P+", "高清 1080P", "高清 720P", "清晰 480P", "流畅 360P"],
        //		"accept_quality": [112, 80, 64, 32, 16],
        //requestFrom: bilibili-helper
        return "https://api.bilibili.com/x/player/playurl?fnval=2&otype=json&avid="+avid+"&fnver=0&qn="+Constant.quality_1080_up+"&player=1&cid="+cid;
    }
}
