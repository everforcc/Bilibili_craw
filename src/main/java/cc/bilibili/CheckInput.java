package cc.bilibili;

import c.c.utils.Bilibili_base58;
import cc.enums.CodeEnum;
import org.apache.commons.lang.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author everforcc 2021-09-06
 */
public class CheckInput {

    /**
     * 校验录入问题
     * 1. 直接录入av，bv
     * 2. 复制链接， 连接可能带?后参数
     * 3.
     *
     * 1.1 av av开头，av后的数字
     * 1.2 bv bv有固定的格式 BV1**4*1*7** 正则 (BV1.{2}4.{1}1.{1}7.{2})
     * 2. 链接 https开头，匹配到后用url来解析
     */

    // av就返回av，bv就算出av，链接就取出av或bv算出av
    public static String checkAV(String input){
        if(StringUtils.isBlank(input)){
            return null;
        }

        if(input.startsWith("https:")){
            String ab = forHttps(input);
            System.out.println("ab >>> " + ab);
            return checkAV(ab);
        }else if(input.startsWith("av")||input.startsWith("AV")){
            return checkAV(input.substring(2,input.length()));
        }else if(input.matches("(BV1.{2}4.{1}1.{1}7.{2})")){
            return String.valueOf(Bilibili_base58.dec(input));
        }else if(input.matches("\\d{1,}")){
            return input;
        }
        // 录入有误，放到枚举里面
        CodeEnum.INPUT_WRONG.wrong();
        return null;
    }

    // 检查ep的录入
    public static void checkEP(){

    }

    // 检查ss的录入
    public static void checkSS(){

    }

    private static String forHttps(String url){
        try {
            URL u = new URL(url);
            String path = u.getPath();
            // 截取最后一个/的位置，然后取出数据
            //path = path.substring(path.lastIndexOf("/")+1,path.length());
            path = path.substring(path.lastIndexOf("/")+1);
            return path;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String url = "https://www.bilibili.com/video/BV1k44y187oM?spm_id_from=333.851.b_62696c695f7265706f72745f67616d65.18";
        String aid = "AV975288337";
        String id = "975288337";
        String bid = "BV1k44y187oM";
        System.out.println("forHttps(url) >>> " + checkAV(url));
    }

}
