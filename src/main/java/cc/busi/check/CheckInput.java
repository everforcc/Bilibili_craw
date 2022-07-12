package cc.busi.check;

import cc.busi.util.Bilibili_base58;
import cc.enums.CodeExceptionEnum;
import cc.exception.CodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author everforcc 2021-09-06
 */
@Slf4j
public class CheckInput {

    /**
     * 校验录入问题
     * 1. 直接录入av，bv
     * 2. 复制链接， 连接可能带?后参数
     * 3.
     * <p>
     * 1.1 av av开头，av后的数字
     * 1.2 bv bv有固定的格式 BV1**4*1*7** 正则 (BV1.{2}4.{1}1.{1}7.{2})
     * 2. 链接 https开头，匹配到后用url来解析
     */

    // av就返回av，bv就算出av，链接就取出av或bv算出av
    public static String checkAV(String input) {

        log.info("录入数据 【{}】", input);

        CodeExceptionEnum.INPUT_WRONG.error(StringUtils.isBlank(input), "用户录入为空");

        if (input.startsWith("https:")) {
            // http开头的链接
            String ab = routes(input);
            log.info("startsWith: " + ab);
            return checkAV(ab);
        } else if (input.startsWith("av") || input.startsWith("AV")) {
            // av格式
            return checkAV(input.substring(2, input.length()));
        } else if (input.matches("(BV1.{2}4.{1}1.{1}7.{2})")) {
            // bv 格式
            return String.valueOf(Bilibili_base58.dec(input));
        } else if (input.matches("\\d{1,}")) {
            // av号 去掉最前面的av
            return input;
        } else {
            // 如果上面格式都匹配不到,就报错
            CodeExceptionEnum.INPUT_WRONG.wrong();
        }

        return null;
    }

    // 检查ep的录入
    public static void checkEP() {

    }

    // 检查ss的录入
    public static void checkSS() {

    }

    private static String routes(String url) {
        try {
            URL u = new URL(url);
            String path = u.getPath();
            // 截取最后一个/的位置，然后取出数据
            // 三种情况
            log.info("path 【{}】", path);
            // 1. 全连接带?号
//            if(path.contains("?")){
//                // 截取最后path为 av或者 bv
//                path = path.substring(0, path.lastIndexOf("/"));
//                path = path.substring(path.lastIndexOf("/") + 1);
//            }else
            {
                int lastIndex = path.lastIndexOf("/") + 1;
                int urlLength = path.length();
                if (urlLength == lastIndex) {
                    // 说明链接最后带 /
                    path = path.substring(0, path.lastIndexOf("/"));
                    path = path.substring(path.lastIndexOf("/") + 1);
                } else {
                    // 截取最后的 番号
                    path = path.substring(path.lastIndexOf("/") + 1);
                }
            }
            return path;
        } catch (MalformedURLException e) {
            throw new CodeException("请检查录入数据");
        }
    }

    public static void main(String[] args) {
        String url = "https://www.bilibili.com/video/BV1k44y187oM?spm_id_from=333.851.b_62696c695f7265706f72745f67616d65.18";
        String aid = "AV975288337";
        String id = "975288337";
        String bid = "BV1k44y187oM";
        System.out.println("forHttps(url) >>> " + checkAV(url));
    }

}
