package cc.bilibili.impl;

import cc.bilibili.ICover;
import cc.constant.ConstantHeader;
import cc.constant.ConstantVideoFlvURL;
import cc.entity.DownMsg;
import cc.utils.IHttp;
import cc.utils.XsoupUtils;
import cc.utils.impl.HttpUrlConnectionUtils;
import cc.utils.impl.JsoupUtils;

import static cc.bilibili.impl.AVFlv.*;

/**
 * @author everforcc 2021-09-07
 */
public class AVCover implements ICover {

    private static IHttp iHttp = new JsoupUtils();
    private static IHttp down = new HttpUrlConnectionUtils();

    private static DownMsg downMsg = new DownMsg();
    private static String htmlByAid(String aid){
        String url = String.format(ConstantVideoFlvURL.videoUrl,aid);
        String html = iHttp.get(url,ConstantVideoFlvURL.GET, ConstantHeader.web);
        return html;
    }

    private static String getImgUrl(String html){
        return XsoupUtils.matchStr(html,ConstantVideoFlvURL.imgXsoupPath);
    }

    static void downFile(DownMsg downMsg){
        down.downFile(downMsg);
    }

    public static void flow(String aid){
        try {
            String html = htmlByAid(aid);
            String imgUrl = getImgUrl(html);
            downMsg.setUrl(imgUrl);

            videoPath(downMsg,aid);
            // downMsg.setFilePath(ConstantDir.av,up,aid,ConstantDir.cover);
            // 后缀名可以截取得到
            downMsg.setFileName(aid + ".jpg");
            downMsg.setHeader(ConstantHeader.web);
            downMsg.setType(ConstantVideoFlvURL.GET);
            downFile(downMsg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        flow("");
    }

}
