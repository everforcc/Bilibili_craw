package c.c.bilibili;

import c.c.utils.Request_Heard;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Yukino
 * 2020/3/3
 */
public class Bilibili_Search {


    @Test
    public void test(){
        try {
            parseHtml("c.c.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parseHtml(String searchWord) throws Exception {
        int total_count = 1;
        Document allhtml = null;
        String urlPath = "https://search.bilibili.com/all?keyword="+encode(searchWord)+"&from_source=nav_search_new&page=";
        HttpURLConnection conn = Request_Heard.requestHeard_FlvUrl(urlPath,"GET");
        try {
            allhtml = Jsoup.parse(conn.getInputStream(), "UTF-8", urlPath+1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer totalPageCount = Integer.valueOf(allhtml.getElementsByClass("page-item last").text());
        System.out.println("totalPageCount:" + totalPageCount);
        for (int i = 1; i <= totalPageCount; i++) {
            allhtml = Jsoup.parse(conn.getInputStream(), "UTF-8", urlPath+i);
            System.out.println("请求成功");
            //System.out.println(allhtml);
            Elements video_list_clearfix = allhtml.getElementsByClass("video-list clearfix");
            System.out.println("video_list_clearfix:" + video_list_clearfix.size());
            for (Element video_list : video_list_clearfix) {
                Elements li_list_elements = video_list.getElementsByTag("li");
                System.out.println(li_list_elements.size());
                for (Element li_element : li_list_elements) {
                    //System.out.println(li_element.child(0));
                    Element li_a = li_element.child(0);

                    String a_href = li_a.getElementsByTag("a").attr("href");
                    System.out.println("第" + total_count++ + "个:" + a_href.substring(2, a_href.length()));
                    System.out.println(a_href.substring(2, a_href.lastIndexOf("?")));
                    //拿到av号就能调用下载了
                }
            }
        }
    }


    /**
     * 模拟浏览器加密
     * @param encode
     * @return
     * @throws Exception
     */
    public static String encode(String encode)throws Exception{


        String encodeStr = URLEncoder.encode(encode, "UTF-8");
        System.out.println(encode+"加密后"+encodeStr);

        String decodeStr = URLDecoder.decode(encodeStr, "UTF-8");
        System.out.println(encodeStr+"解密后:"+decodeStr);

        return encodeStr;
    }


}
