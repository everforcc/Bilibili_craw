package cc.busi.cover.flow.impl;

import cc.busi.check.CheckInput;
import cc.busi.cover.flow.ICover;
import cc.constant.ConstantDir;
import cc.constant.ConstantHeader;
import cc.busi.video.constant.ConstantVideoFlvURL;
import cc.constant.ConstantReqType;
import cc.entity.DownMsg;
import cc.utils.http.IHttp;
import cc.utils.http.impl.HttpUrlConnectionUtils;
import cc.utils.http.impl.JsoupUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author everforcc 2021-09-07
 */
@Slf4j
public class AVCover implements ICover {

    private static IHttp iHttp = new JsoupUtils();
    private static IHttp down = new HttpUrlConnectionUtils();

    private static DownMsg downMsg = new DownMsg();

    /**
     * 从录入提取 av号
     *
     * @param input 录入
     * @return 番号
     */
    public String urlToAV(String input) {
        log.info("输入aid 【{}】", input);
        String avCode = CheckInput.checkAV(input);
        log.info("返回aid 【{}】", avCode);
        return avCode;
    }

    /**
     * 从 aid 获取html
     *
     * @param aid 番号
     * @return 返回html
     */
    public String htmlByAid(String aid) {
        String url = String.format(ConstantVideoFlvURL.videoUrl, aid);
        return iHttp.get(url, ConstantReqType.GET, ConstantHeader.web);
    }

    /**
     * 从 html 获取 封面
     *
     * @param html html
     * @return 封面链接
     */
    public String getImgUrl(String html) {
        //return XsoupUtils.matchStr(html,ConstantVideoFlvURL.imgXsoupPath);
        Document d1 = Jsoup.parse(html);// 转换为Dom树
        Elements elements_meta = d1.getElementsByTag("meta");
        int size = elements_meta.size();
        for (int i = 10; i < size; i++) {
            Element element_url = elements_meta.get(i);
            if ("image".equals(element_url.attr("itemprop"))) {
                return element_url.attr("content");
            }
        }
        return null;
    }

    public DownMsg getDownMsg(String imgUrl, String aid, String filePath) {
        DownMsg downMsg = new DownMsg();
        downMsg.setUrl(imgUrl);


        if (StringUtils.isNotEmpty(filePath)) {
            downMsg.setFilePath(filePath);
        } else {
            String up = String.format(ConstantDir.d2_up_idFormat, "pic", "pic");
            downMsg.setFilePath(up, ConstantDir.av_flv, aid);
        }

        // 后缀名可以截取得到
        downMsg.setFileName("[av" + aid + "].jpg");
        downMsg.setHeader(ConstantHeader.web);
        downMsg.setReqType(ConstantReqType.GET);
        return downMsg;
    }

    /**
     * 下载文件
     *
     * @param downMsg 文件信息
     */
    public void downFile(DownMsg downMsg) {
        down.downFile(downMsg);
    }

}
