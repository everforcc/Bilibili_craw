package c.c.bilibili;

import c.c.thread.ThreadGroupDown;
import c.c.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Yukino
 * 2020/3/3
 */
public class Bilibili_Video_Flv {

    /**
     * UP主ID
     */
    //private static String poster_uid;

    private static Print_Record println = Print_Record.getInstanse("");;

    // aid和bid转换使用
    private static BilHelper bilHelper = new BilHelper();

    /**
     * 下载指定av号
     */
    Bilibili_Video_Flv() {
        // 可以不传参数，下载给个号就行
    }

    /**
     * 根据up id下载全集
     */
    /*Bilibili_Video_Flv(String poster_uid) {
        println = Print_Record.getInstanse(poster_uid);
        this.poster_uid = poster_uid;
    }*/

    // 下载单个视频
    static void downAV(String aid){
        ThreadGroupDown threadGroupDown = new ThreadGroupDown(downNeedMsg(aid));
        threadGroupDown.run();
    }

    /**
     * 1.根据up ID 获取所有AV号
     * 2.获取视频个数
     * 3.重新构造请求地址
     */
    static void requestFlow(String poster_uid) {
        try {
            String flv_vlist = (String) flvCount(Request_Method.js_commom(ConstantURL.allFlvUrl(poster_uid,1,1),null,"GET"), Constant.count);// 1.获取用户视频总数
            println.println("flv_vlist:"+flv_vlist);
            // 没有和0都没有意义，不用进来
            if(null!=flv_vlist||!"0".equals(flv_vlist)) {
                // b站视频最多一次请求100个
                int count = Integer.valueOf(flv_vlist);
                int pn = count / 100;
                int remainder = count % 100;
                List<String> aid_list = new ArrayList<String>();
                // 处理前几页
                for (int i = 1; i <= pn + 1; i++) {
                    println.println("正在获取第" + i + "页");
                    List<String> aid_list_i = (List<String>) flvCount(Request_Method.js_commom(ConstantURL.allFlvUrl(poster_uid, 100, i), null, "GET"), Constant.aid); // 2.获取用户所有的av号
                    for (String str_aid : aid_list_i) {
                        aid_list.add(str_aid);
                    }
                }
                // 假如给的值超出了，会按照最多的计算
            /*if(remainder!=0){ // 处理最后一页
                int last = pn+1;
                println.println("正在获取第"+ last +"页");
                List<String> aid_list_i = (List<String>) flvCount(Request_Method.js_commom(allFlvUrl(poster_uid, remainder,last), null, "GET"), Constant.aid); // 2.获取用户所有的av号
                for(String str_aid:aid_list_i){
                    aid_list.add(str_aid);
                }
            }*/

                println.println("一共有" + aid_list.size() + "组视频待下载");
                List<String[]> strList = new ArrayList<>();
                ThreadGroup downGroup = new ThreadGroup("down");
                List<String[]> listGroupData = new ArrayList<>();
                //av 号码的集合
                for (String aid : aid_list) { // av号集合
                    //根据av号下载
                    strList = downNeedMsg(aid);
                    strList.forEach(listGroupData::add);
                }
                ThreadGroupDown threadGroupDown = new ThreadGroupDown(listGroupData);
                threadGroupDown.run();

            }
        } catch (Exception e) {
            e.printStackTrace();
            println.printErrln("下载up全部视频异常:" + e.toString());
        }
    }

    /**
     *  1. 用户ID 请求获取AV号的集合
     * @param json
     * @param type
     * @return
     */
    private static Object flvCount(String json, String type){
        JSONObject jsonObject = JSONObject.fromObject(json);

        if(!"0".equals(jsonObject.get("code").toString())){
            try {
                throw new Exception(jsonObject.get("message").toString());
            } catch (Exception e) {
                println.println("flvCount()返回的失败json:" + json + "---" + e.toString());
                return null;
            }
        }

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
    private static List<Map<String,String>> flvMsgList(String cidJson){
        List<Map<String,String>> cid_list = new ArrayList<Map<String,String>>();
        Map<String,String> cid_map;

        JSONObject jsonObject = JSONObject.fromObject(cidJson);
        if(!"0".equals(jsonObject.get("code").toString())){
            try {
                throw new Exception(jsonObject.get("message").toString());
            } catch (Exception e) {
                println.println("flvMsgList():返回的失败json:" + cidJson + "---" + e.toString());
                return null;
            }
        }
        JSONObject json_date = jsonObject.getJSONObject("data");

        String title = json_date.getString("title");
        JSONObject json_date_owner = json_date.getJSONObject("owner");
        String mid = json_date_owner.getString("mid");
        String name = json_date_owner.getString("name");
        JSONArray json_date_pages = json_date.getJSONArray("pages");
        if(json_date_pages.size()!=1){
            System.out.println(json_date.getString("aid"));
        }
        for(int i=0;i<json_date_pages.size();i++){
            cid_map = new HashMap<String, String>();
            JSONObject json_date_page = (JSONObject)json_date_pages.get(i);
            cid_map.put("cid",json_date_page.getString("cid"));
            cid_map.put("part",json_date_page.getString("part"));
            cid_map.put("owner",mid+"_"+name);
            cid_map.put("title",title);
            cid_list.add(cid_map);
        }
        println.println("需要的参数:"+cid_list);
        return cid_list;
    }
    /**
     *  4.根据cid和aid获得的json去取出视频的真实地址
     * @param json
     * @return
     */
    private static List<String> flvUrlList(String json){
        // 这个位置已经找到了视频的地址，应该不会储问题
        JSONObject jsonObject = JSONObject.fromObject(json);
        if(!"0".equals(jsonObject.get("code").toString())){
            try {
                throw new Exception(jsonObject.get("message").toString());
            } catch (Exception e) {
                println.println("flvUrlList():返回的失败json:" + json + "---" + e.toString());
                return null;
            }
        }
        JSONObject json_date = jsonObject.getJSONObject("data");
        JSONArray json_date_durl = json_date.getJSONArray("durl");
        List<String> url_list = new ArrayList<String>();

        // 这个未知实际上只有一个，但是返回的是个数组，就先这样子取吧，没有影响
        for(int i=0;i<json_date_durl.size();i++){
            JSONObject json_date_durl_page = (JSONObject)json_date_durl.get(i);
            url_list.add(json_date_durl_page.getString("url"));// 这个是主要地址还有个backup_url
        }
        println.println("需要的参数 "+ url_list.size() + "个:" +url_list);
        return url_list;
    }


    /**
     * 5.根据av号返回下载所需信息
     * @param aid
     * @return
     */
    private static List<String[]> downNeedMsg(String aid) {
        println = Print_Record.getInstanse(aid);
        println.println("");
        aid = bilHelper.inputToAV(aid);
        println.println("下载视频的AV号:" + aid);
        //获取对应的cid
        // js();
        // 3.获取cid相关信息  AV号码相关的cid集合
        // 单独设置请求头
        List<Map<String, String>> cidMapList = null; // 3.根据av号查询出对应的真实cid
        try {
            cidMapList = flvMsgList(Request_Method.js_headers(ConstantURL.getCidUrl(aid), Constant.GET));
        } catch (Exception e) {
            println.printErrln("获取cid时出现异常:"+aid);
            e.printStackTrace();
        }
        List<String[]> downMsg = new ArrayList<>();
        if(null!=cidMapList&&!cidMapList.isEmpty()) {
            for (Map<String, String> map : cidMapList) { // cid集合
                String cid = map.get("cid");
                //map.get("part");
                // 根据cid和aid请求flv地址
                // js() 请求具体视频的地址
                // 4.获取到视频的具体地址
                List<String> url_list = null; // 4.根据av号和cid获取真实视频的地址
                try {
                    url_list = flvUrlList(Request_Method.js_headers(ConstantURL.getFlvUrl(aid, cid), Constant.GET));
                } catch (Exception e) {
                    println.printErrln("获取视频时时出现异常---aid:" + aid + "，cid:" + cid);
                    e.printStackTrace();
                }

                //  这个是返回的就是集合，但是见到的都是一个的，所以先这样没问题
                for (int j = 0; j < url_list.size(); j++) { // 视频地址集合，有主要的有备用的，目前只取了主要的
                    String flvUrl = url_list.get(j).replaceAll("\\u0026", "&"); //视频地址的 \u0026 这个表示&符转换一下
                    println.println("具体视频url:" + flvUrl);
                    //5.下载  后缀名待完善 , 再加个aid 为好
                    // 目录结构 视频根目录加上 归属人
                    String dir = Constant.dir_video + File.separator + map.get("owner") + File.separator + map.get("title") ;
                    // 命名规则
                    // String fileName = "aid" + aid + "_cid" + cid + "_" + map.get("title") + "_" + map.get("part") + ".flv";
                    String fileName = map.get("title") + "_" + map.get("part") + Bilibili_Down.qualityStr + ".flv";
                    // 下载所需的信息
                    downMsg.add(new String[]{flvUrl, "av"+ aid, dir, fileName, Constant.GET});
                }
            }
        }
        return downMsg;
    }


}
