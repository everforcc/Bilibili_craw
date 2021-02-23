package c.c.bilibili;

import c.c.utils.BilHelper;
import c.c.utils.Constant;
import c.c.utils.Method_down;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Yukino
 * 2020/3/3
 */
public class Bilibili_Cover {

    /**
     * 用最基础的正则表达式来匹配
     * 这一块儿目前不是很完善，有兴趣的自行完善
     */


    /**
     * 随后修改为直接 从链接  获取封面
     */

    /**
     * 根据av号获取html
     * @param avnum
     * @throws Exception
     */
    public static void getImgByAV(String avnum) throws Exception{
        BilHelper bilHelper = new BilHelper();
        //avnum="69345392";
        Connection connection = Jsoup.connect("https://www.bilibili.com/video/av"+ bilHelper.inputToAV(avnum));// 获取连接
        connection.header("User-Agent",Constant.userAgent);// 配置模拟浏览器
        Connection.Response login = connection.execute();// 获取响应
        Document d1 = Jsoup.parse(login.body());// 转换为Dom树
        List<Element> et = d1.select("meta[itemprop]");// 获取form表单，可以通过查看页面源码代码得知

        for (Element element : et) {
            //正则匹配url
            Pattern pattern = Pattern.compile(Constant.regex);
            Matcher matcher = pattern.matcher(element.attr("content"));
            //是否匹配到了
            if (matcher.matches()) {
                System.out.println(element.attr("content"));
                String url=element.attr("content");
                //如果匹配到了下载
                Method_down.downByUrl(element.attr("content"),"封面\\",avnum+"." + url.substring(url.lastIndexOf(".")+1,url.length()));//传图片后坠
            }
        }
    }
}
