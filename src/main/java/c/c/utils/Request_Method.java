package c.c.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Yukino
 * 2020/3/3
 */
public class Request_Method {
    private static Print_Record println = Print_Record.getInstanse("");
    /**
     * 用来处理普通的没有特殊请求头的js请求
     * 用来获取数据
     * 第二个参数不用的时候传 null
     * @param urlPath
     * @param sendMsg
     * @param requistType
     * @return
     * @throws Exception
     */
    public static String js_commom(String urlPath,String sendMsg,String requistType)throws Exception{
        println.println("js_commom请求地址:"+urlPath);
        //2, 打开连接
        HttpURLConnection conn = Request_Heard.request_UserAgent(urlPath,sendMsg,requistType);
        //得到服务器写回的响应数据
        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
        String str = br.readLine();
        println.println("js_commom响应内容为:  " + str);
        return  str;
    }

    /**
     * 为了获取json数据
     * @param urlPath
     * @param requestType
     * @return
     * @throws Exception
     */
    public static String js_headers(String urlPath,String requestType)throws Exception{
        println.println("js_headers请求地址为:"+urlPath);
        HttpURLConnection conn = Request_Heard.requestHeard_FlvUrl(urlPath,requestType);
        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String str;
        StringBuffer content = new StringBuffer("");
        while((str=br.readLine())!=null) {
            content.append(str);
        }
        println.println("js_headers响应内容为:" + content);
        return  content.toString();
    }

    public static String js_headersEP(String urlPath,String requestType)throws Exception{
        println.println("js_headers请求地址为:"+urlPath);
        //https://api.bilibili.com/pgc/player/web/playurl?fnver=0&qn=16&ep%5Fid=330669&otype=json&fnval=0&bvid=BV1Fp4y1S7yn&avid=968751305&player=1&cid=210828639
        //https://api.bilibili.com/pgc/player/web/playurl?ep%5Fid=330669&avid=968751305&fnver=0&player=1&bvid=BV1Fp4y1S7yn&otype=json&qn=16&fnval=0&cid=210828639
        /*HttpURLConnection conn = Request_Heard.requestHeard_downEPFlv(urlPath,requestType);
        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String str;
        StringBuffer content = new StringBuffer("");
        while((str=br.readLine())!=null) {
            content.append(str);
        }
        println.println("js_headers响应内容为:" + content);*/
        Connection connection = Jsoup.connect(urlPath)
                .cookie("Cookie",Constant.cookie)
                .userAgent(Constant.userAgent)
                .method(Connection.Method.GET)

                .data("accept","application / json")
                .data("accept-encoding","gzip, deflate, br")
                .data("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                .data("cookie","CURRENT_FNVAL=80; _uuid=9AFCEABD-AA14-7A49-C49D-FD99ACED501F10305infoc; blackside_state=1; rpdid=|(J|)J|uk|~R0J'uY|RRu|ukm; DedeUserID=58572396; DedeUserID__ckMd5=20e54bd3090b9e60; SESSDATA=6b67619a%2C1619964231%2C4587d*b1; bili_jct=daef3ff66fb5ebd0a6ea6bcfad6fd37f; CURRENT_QUALITY=120; LIVE_BUVID=AUTO9316047559928202; flash_player_gray=false; html5_player_gray=false; buvid3=9CCF06F2-A16F-4C4D-A8D1-7965FEC7E628138365infoc; bp_video_offset_58572396=457129969363782471; bp_t_offset_58572396=457129969363782471; bsource=search_baidu; sid=9hdg9911; PVID=2; bfe_id=1e33d9ad1cb29251013800c68af42315")
                .data("referer","https://www.bilibili.com/bangumi/play/ss32998")
                .data("sec-fetch-mode","no-cors")
                .data("sec-fetch-site","cross-site")
                .data("user-agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                .data("x-requested-with","ShockwaveFlash/29.0.0.171");
        String response = connection.execute().body();
        return  response;
    }

    /**
     * 用来处理普通的没有特殊请求头的，
     * 获取html
     * @param url
     * @throws Exception
     */
    public static String jsonByJsoup(String url)throws Exception{
        // 返回的数据，只有一行，包含了需要的信息 fid  aid bid cid(电影有两p的情况吗?没遇到过,再说)
        Connection connection = Jsoup.connect(url)
                .cookie("Cookie",Constant.cookie)
                .userAgent(Constant.userAgent)
                .method(Connection.Method.GET);
        String response = connection.execute().body();

        return response;
    }

    public static String jsonByJsoupChar(String url,String id)throws Exception{

        HttpURLConnection conn = Request_Heard.requestHeard_downEPFlv(url,id);
        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        String str;
        StringBuffer content = new StringBuffer("");
        while((str=br.readLine())!=null) {
            content.append(str);
        }
        println.println("js_headers响应内容为:" + content);
        return content.toString();
    }

}
