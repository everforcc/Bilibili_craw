package c.c.bilibili;

import c.c.thread.ThreadGroupDown;
import c.c.utils.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bilibili_ep_Flv {

    /*public static void main(String[] args) {
        epFlow("ep330669");
    }*/

    private static Print_Record print_record = Print_Record.getInstanse("");
    static void epFlow(String epid) {
        // 组织下载信息
        List<String[]> downMsg = new ArrayList<>();
        String start ="" ;
        if(null!=epid&&(epid.startsWith("ep")||epid.startsWith("ss"))){
            start = epid.substring(0,2);
            epid = epid.substring(2,epid.length());
        }else {
            try {
                throw new Exception("录入id有误");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            String response = Request_Method.jsonByJsoup(ConstantURL.epUrlPlay(start + epid));
            Map<Integer,Map<String,String>> map = matchupdateParent(response);
            if(null!=map||map.size()!=0){
                for(int index=0;index<map.size();index++) {
                    Map<String,String> mapAry = map.get(index);
                    String url = ConstantURL.epUrlPlayurl(mapAry.get("id"), mapAry.get("aid"), mapAry.get("bvid"), mapAry.get("cid"));
                    print_record.println("url---"+url);
                    String id = start + mapAry.get("id");
                    // 用url地址获取视频地址
                    String epUrl = epUrlByJson(url,id);
                    print_record.println("epUrl---"+epUrl);
                    String dir = "";
                    if(start.equals("ep")){
                        dir = Constant.dir_comic;
                    }else {
                        dir = Constant.dir_movie;
                    }
                    // 文件命名和目录
                    String title = Common_Method.checkFileName(mapAry.get("title"));
                    dir += File.separator + title + File.separator;
                    String fileName = mapAry.get("titleFormat")+mapAry.get("longTitle") + ".flv";
                    // 开始下载视频
                    downMsg.add(new String[]{epUrl, id, dir, fileName, Constant.GET});
                }
            }
            print_record.println("待下载视频个数:"+downMsg.size());
            if(downMsg.size()!=0) {
                ThreadGroupDown threadGroupDown = new ThreadGroupDown(downMsg);
                threadGroupDown.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*public static String epUrl(String ep,String aid,String bvid,String cid){
        return "https://api.bilibili.com/pgc/player/web/playurl?ep%5Fid=" + ep
                + "&avid=" + aid + "&fnver=0&player=1&bvid=" + bvid + "&otype=json&qn=" + Constant.quality_360 + "&fnval=0&cid=" + cid + "";
    }*/

    private static Map<Integer,Map<String,String>> matchupdateParent(String aHtml) {
        Map<Integer,Map<String,String>> mapAry = new HashMap<>();
        String regexStart="\\<script\\>window\\.__INITIAL_STATE__=";
        String regexEnd=";\\(function\\(\\)\\{var s;\\(s=document\\.currentScript\\|\\|document\\.scripts\\[document\\.scripts\\.length-1\\]\\)\\.parentNode\\.removeChild\\(s\\);\\}\\(\\)\\);\\</script\\>";
        // String regex = "\\<script\\>window\\.__INITIAL_STATE__=.*;\\(function\\(\\)\\{var s;\\(s=document\\.currentScript\\|\\|document\\.scripts\\[document\\.scripts\\.length-1\\]\\)\\.parentNode\\.removeChild\\(s\\);\\}\\(\\)\\);\\</script\\>" ;
        String regex = regexStart +".*" + regexEnd ;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(aHtml);
        //是否匹配到了 , 只能匹配一个
        String json="";
        int i = 0;
        if (matcher.find()) {
            json = matcher.group(0);
            // 替换掉前缀和后缀，不用计算的方式，那样比较慢
            json = json.replaceAll(regexStart,"").replaceAll(regexEnd,"");
            i++;
        }
        if(i==1) {
            // 这里截取json
            /*json = json.substring(json.indexOf("{"),json.lastIndexOf("}"));
            json = json.substring(0,json.lastIndexOf("}")+1);*/
            print_record.println(json);
            JSONObject jsonObjectAllMsg = (JSONObject)JSON.parseObject(json); //多电影可能是这个位置
            //String h1Title = jsonObjectAllMsg.getString("h1Title");
            JSONArray jsonArray = jsonObjectAllMsg.getJSONArray("epList");
            for(int index = 0;index<jsonArray.size();index++) {
                Map<String, String> map = new HashMap<>();
                JSONObject jsonObjectVideoMsg = (JSONObject) jsonArray.get(index);
                map.put("aid", jsonObjectVideoMsg.getString("aid"));
                map.put("id", jsonObjectVideoMsg.getString("id"));
                map.put("bvid", jsonObjectVideoMsg.getString("bvid"));
                map.put("cid", jsonObjectVideoMsg.getString("cid"));
                map.put("badgeColor", jsonObjectVideoMsg.getString("badgeColor"));
                map.put("titleFormat", jsonObjectVideoMsg.getString("titleFormat"));
                JSONObject jsonObjectMediaInfo = jsonObjectAllMsg.getJSONObject("mediaInfo");
                map.put("longTitle", jsonObjectMediaInfo.getString("longTitle"));
                map.put("title", jsonObjectMediaInfo.getString("title") );
                mapAry.put(index, map);
            }
            return  mapAry;
        }else {
            try {
                throw new Exception("ep返回json格式化出现问题");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new HashMap<>();
    }


    private static String epUrlByJson(String url,String id){

        try {
            String json = Request_Method.jsonByJsoupChar(url,id);
            JSONObject jsonObject = JSON.parseObject(json);
            if("0".equals(jsonObject.getString("code"))){
                JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("durl");
                //for(int i=0;i<jsonArray.size();i++){ //目前只发现第一个，后续的遇到了再说
                JSONObject jsonAryDurl = (JSONObject)jsonArray.get(0);
                return jsonAryDurl.getString("url");
                //}
            }else {
                throw new Exception(jsonObject.getString("message"));
            }
        } catch (Exception e) {
            print_record.printErrln("获取视频地址出错");
            e.printStackTrace();
        }

        return "";
    }

}
