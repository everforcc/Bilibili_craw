package cc.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

/**
 * @author guokailong 2021-09-07
 */
public class XsoupUtils {

    // //meta[12]/@content
    public static String matchStr(String html,String regex){
        Document document = Jsoup.parse(html);// 转换为Dom树
        String result = Xsoup.compile(regex).evaluate(document).get();
        return result;
    }

}
