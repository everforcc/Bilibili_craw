package c.c.bilibili;

import c.c.utils.Constant;
import c.c.utils.Method_down;
import c.c.utils.Request_Method;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Yukino
 * 2020/3/3
 */
public class Bilibili_Flv {

    //bilibili的视频地址的请求目前都是GET

    public void requestFlow() {
        try {
            //poster_uid="474672071";
            //发送请求的流程
            int totalcount=1;
            //1.根据up ID 获取所有AV号
            //
            //获取视频个数
            //重新构造请求地址
            // 前两个使用GET请求
            String flv_vlist = (String) flvCount(Request_Method.js_commom(allFlvUrl(poster_uid,"1"),null,"GET"), Constant.count);// 1.获取用户视频总数
            System.out.println("flv_vlist:"+flv_vlist);
            //2. https://api.bilibili.com/x/web-interface/view?aid=170001
            List<String> aid_list = (List<String>) flvCount(Request_Method.js_commom(allFlvUrl(poster_uid,flv_vlist),null,"GET"), Constant.aid); // 2.获取用户所有的av号
            //av 号码的集合
            for (String aid : aid_list) { // av号集合
                //根据av号下载
                downAV(aid,true);
                System.out.println("一共下载了:"+totalcount+++"个视频");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * UP主ID
     */
    private  String poster_uid;

    /**
     * 根据up id下载全集
     * @param poster_uid
     */
    public Bilibili_Flv(String poster_uid) {
        this.poster_uid = poster_uid;
    }

    /**
     * 下载指定av号
     */
    public Bilibili_Flv() {
    }

    /**
     * 根据给定up的id来请求
     * 为了获取所有视频的地址
     * @param mid
     * @param ps
     * @return
     */
    public String allFlvUrl(String mid,String ps){
        // pn 页码 mid up id  ps pagesize页面尺寸
        return "https://api.bilibili.com/x/space/arc/search?tid=0&pn=1&keyword=&order=pubdate&jsonp=jsonp&ps="+ps+"&mid="+mid;
    }

    /**
     *  获取视频对应的cid
     * @param aid
     * @return
     */
    public String getCidUrl(String aid){
        return "https://api.bilibili.com/x/web-interface/view?aid="+aid;
    }

    /**
     * 获取具体视频的url
     * @param avid
     * @param cid
     * @return
     */
    public String getFlvUrl(String avid,String cid){
        //cid: 152777757
        //avid: 89453968
        //qn: 80  视频质量
        //otype: json
        //      "accept_format": "hdflv2,flv,flv720,flv480,mp4",
        //		"accept_description": ["高清 1080P+", "高清 1080P", "高清 720P", "清晰 480P", "流畅 360P"],
        //		"accept_quality": [112, 80, 64, 32, 16],
        //requestFrom: bilibili-helper
        return "https://api.bilibili.com/x/player/playurl?fnval=2&otype=json&avid="+avid+"&fnver=0&qn=80&player=1&cid="+cid;
    }



    /**
     *  1. 用户ID 请求获取AV号的集合
     * @param json
     * @param type
     * @return
     */
    public Object flvCount(String json, String type){
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONObject json_date = jsonObject.getJSONObject("data");
        List<String> aid_list = new ArrayList<String>();
        switch (type){
            case "count":
                //获取视频总个数
                JSONObject json_data_page = json_date.getJSONObject("page");
                String page_count = json_data_page.getString("count");
                return page_count;
            case "aid":
                // 获取upav号集合
                JSONObject json_date_list = json_date.getJSONObject("list");
                JSONArray json_date_list_vlist = json_date_list.getJSONArray("vlist");
                for(int i=0;i<json_date_list_vlist.size();i++){
                    JSONObject json_date_list_list = (JSONObject)json_date_list_vlist.get(i);
                    aid_list.add(json_date_list_list.getString("aid"));
                }
                return aid_list;
        }
        return "";
    }

    /**
     *  2.获取cid相关信息
     *   cid才是视频的真实id
     * @param cidJson
     * @return
     */
    public List<Map<String,String>> flvMsgList(String cidJson){
        JSONObject jsonObject = JSONObject.fromObject(cidJson);
        JSONObject json_date = jsonObject.getJSONObject("data");
        List<Map<String,String>> cid_list = new ArrayList<Map<String,String>>();
        Map<String,String> cid_map;
        JSONArray json_date_pages = json_date.getJSONArray("pages");
        for(int i=0;i<json_date_pages.size();i++){
            cid_map = new HashMap<String, String>();
            JSONObject json_date_page = (JSONObject)json_date_pages.get(i);
            cid_map.put("cid",json_date_page.getString("cid"));
            cid_map.put("part",json_date_page.getString("part"));
            cid_list.add(cid_map);
        }
        return cid_list;
    }
    /**
     *  4.根据cid和aid获得的json去取出视频的真实地址
     * @param json
     * @return
     */
    public List<String> flvUrlList(String json){
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONObject json_date = jsonObject.getJSONObject("data");
        JSONArray json_date_durl = json_date.getJSONArray("durl");
        List<String> url_list = new ArrayList<String>();

        //没搞明白这个集合的作用
        for(int i=0;i<json_date_durl.size();i++){
            JSONObject json_date_durl_page = (JSONObject)json_date_durl.get(i);
            url_list.add(json_date_durl_page.getString("url"));
        }

        return url_list;
    }

    /**
     *  5.根据av号下载av
     * @param aid
     * @throws Exception
     */
    public void downAV(String aid,Boolean down)throws Exception{
        System.out.println("AV:" + aid);
        //获取对应的cid
        // js();
        // 3.获取cid相关信息  AV号码相关的cid集合
        // 单独设置请求头
        List<Map<String, String>> cidMapList = flvMsgList(Request_Method.js_headers(getCidUrl(aid),"GET")); // 3.根据av号查询出对应的真实cid
        for (Map<String, String> map : cidMapList) { // cid集合
            String cid =map.get("cid");
            //map.get("part");
            // 根据cid和aid请求flv地址
            // js() 请求具体视频的地址
            // 4.获取到视频的具体地址
            List<String> url_list = flvUrlList(Request_Method.js_headers(getFlvUrl(aid,cid),"GET")); // 4.根据av号和cid获取真实视频的地址
            for (int j=0;j<url_list.size();j++) { // 视频地址集合，有主要的有备用的，目前只取了主要的
                String flvUrl = url_list.get(j).replaceAll("\\u0026", "&"); //视频地址的 \u0026 这个表示&符转换一下
                System.out.println("具体视频url:" + flvUrl);
                if(down) {
                    //5.下载  后缀名待完善 , 再加个aid 为好
                    String dir="视频\\" + poster_uid;
                    String fileName="aid" + aid + "cid" + cid + map.get("part") + j + ".flv";
                    Method_down.downFlv(flvUrl , aid, dir , fileName,"GET"); //根据地址下载视频
                }
            }
        }
    }


}
