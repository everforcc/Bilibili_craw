package cc.busi.ep.flow;

import cc.busi.IVideo;
import cc.busi.check.CheckReturn;
import cc.busi.ep.dto.EPVideoVO;
import cc.constant.*;
import cc.entity.DownMsg;
import cc.enums.CodeExceptionEnum;
import cc.utils.http.IHttp;
import cc.utils.http.impl.HttpUrlConnectionUtils;
import cc.utils.http.impl.JsoupUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author everforcc 2021-10-09
 */
@Slf4j
public class EPFLV implements IVideo {

    private static String ep;
    private static DownMsg downHTML = new DownMsg();
    private static DownMsg downMsg = new DownMsg();
    private static IHttp iHttp = new JsoupUtils();
    private static IHttp down = new HttpUrlConnectionUtils();

    /**
     * 2.
     *
     * @param inputEP
     * @return
     */
    public String getHTMLByep(String inputEP) {
        ep = inputEP;
        String html;
        if (StringUtils.isNotBlank(html = readHtml(ep, ConstantCommon.HTML))) {
            log.info("已存在，不重复读取");
            return html;
        }
        log.info(ep + "不存在，开始读取");
        // 不能为空
        CodeExceptionEnum.AID_NULL.error("".equals(ep));
        // 根据id请求必须有返回数据
        // 根据aid拿到cid的数据
        String urlPath = String.format(ConstantEPFlvURL.rootUrl, ep);
        html = iHttp.get(urlPath, ConstantVideoFlvURL.aidToCidType, ConstantHeader.epFlv, inputEP);
        saveHtml(html, ep, ConstantCommon.HTML);
        return html;
    }

    /**
     * 3.
     *
     * @param html
     * @return
     */
    public String matchJSON(String html) {
        String json;
        if (StringUtils.isNotBlank(json = readHtml(ep, ConstantCommon.JSON))) {
            log.info("JSON已存在，不重复读取");
            return json;
        }

        Pattern pattern = Pattern.compile(ConstantEPFlvURL.regex);
        Matcher matcher = pattern.matcher(html);
        //是否匹配到了 , 只能匹配一个
        int i = 0;
        if (matcher.find()) {
            json = matcher.group(1);
            log.info("json:" + json);
            saveHtml(json, ep, ConstantCommon.JSON);
        }
        return json;
    }

    /**
     * 4.
     *
     * @param json
     * @return
     */
    public List<DownMsg> getFileMsg(String json) {
        List<DownMsg> downMsgList = new ArrayList<>();
        // TODO 校验json
        EPVideoVO epVideoVO = JSON.parseObject(json, EPVideoVO.class);
        int i = 0;
        for (EPVideoVO.ep epVideoEp : epVideoVO.getEpList()) {
            String url = String.format(ConstantEPFlvURL.epUrlPlayurl, epVideoEp.getId(), epVideoEp.getAid(), epVideoEp.getBvid(), epVideoEp.getCid(), ConstantQuality.quality_1080_60);
            log.info("url:" + url);
            String realVideojson = iHttp.get(url, ConstantVideoFlvURL.GET, ConstantHeader.epFlv, epVideoEp.getAid());

            log.info("realVideojson:" + realVideojson);
            String realFlvUrlurl = getRealFlvUrl(realVideojson);
            log.info("realFlvUrlurl:" + realFlvUrlurl);
            downMsg.setUrl(realFlvUrlurl);
            downMsg.setFilePath(ConstantDir.ep, ep, epVideoEp.getTitle());
            // downMsg.setFilePath(ConstantDir.av,up,aid,ConstantDir.video);
            // TODO 可以手动格式化个格式 [AV][PART].flv
            downMsg.setFileName(epVideoVO.getH1Title() + i++ + ConstantVideoFlvURL.downFileTypeFlv);
            downMsg.setType(ConstantVideoFlvURL.downFileUrlType);
            downMsg.setHeader(ConstantHeader.mapFlv);
            downMsgList.add(downMsg);
        }
        return downMsgList;
    }

    /**
     * 视频真实url
     *
     * @param json
     * @return
     */
    private static String getRealFlvUrl(String json) {
        // 格式化
        JSONObject jsonObject = JSON.parseObject(json);
        // 校验
        CheckReturn.checkJson(jsonObject);
        // url在json中的位置
        JSONObject realUrlObj = jsonObject.getJSONObject("result").getJSONArray("durl").getJSONObject(0);
        String realUrl = realUrlObj.getString("url");
        return realUrl;
    }

    private static void saveHtml(String str, String ep, String type) {
        downHTML.setContent(str);
        downHTML.setFilePath(ConstantDir.ep, ep);
        downHTML.setFileName(ep + type);
        down.saveFile(downHTML);
    }

    private static String readHtml(String ep, String type) {
        downHTML.setFilePath(ConstantDir.ep, ep);
        downHTML.setFileName(ep + type);
        return down.readFile(downHTML);
    }

    /**
     * 5.
     *
     * @param downMsgList
     */
    public void downFile(List<DownMsg> downMsgList) {
        // 地址，文件路径，文件名。type，headers
        for (DownMsg downMsg : downMsgList) {
            down.downFile(downMsg);
        }
        //downMsgList.forEach(iHttp::downFile);
        /*for(DownMsg downMsg:downMsgList){
            try {
                Method_down.downFlv(downMsg.getUrl(), "av36953163", "craw\\电影\\正片\\正片-4.flv", downMsg.getFileName(), ConstantVideoFlvURL.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

}
