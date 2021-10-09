package cc.bilibili.impl;

import cc.bilibili.IVideo;
import cc.constant.*;
import cc.entity.DownMsg;
import cc.enums.CodeEnum;
import cc.utils.IHttp;
import cc.utils.impl.HttpUrlConnectionUtils;
import cc.utils.impl.JsoupUtils;
import cc.vo.EPVideoVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cc.bilibili.impl.AVFlv.checkJson;
import static cc.bilibili.impl.AVFlv.downFile;

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

    private static String getHTMLByep(String inputEP){
        ep = inputEP;
        String html;
        if(StringUtils.isNotBlank(html = readHtml(ep, Constant.HTML))) {
            log.info("已存在，不重复读取");
            return html;
        }
        log.info(ep + "不存在，开始读取");
        // 不能为空
        CodeEnum.AID_NULL.isEffect("".equals(ep));
        // 根据id请求必须有返回数据
        // 根据aid拿到cid的数据
        String urlPath = String.format(ConstantEPFlvURL.rootUrl,ep);
        html = iHttp.get(urlPath,ConstantVideoFlvURL.aidToCidType, ConstantHeader.mapFlv,ConstantVideoFlvURL.charset);
        saveHtml(html,ep,Constant.HTML);
        return html;
    }

    public static void flow(String ep){
        // 1.获得html
        String html = getHTMLByep(ep);
        // 2.正则匹配到js
        String json = matchJSON(html);
        // 3. 拿到url组装下载信息
        List<DownMsg> downMsgList = getFileMsg(json);
        // 4. down
        downFile(downMsgList);
    }

    private static String matchJSON(String html){
        String json;
        if(StringUtils.isNotBlank(json = readHtml(ep, Constant.JSON))) {
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
            saveHtml(json,ep,Constant.JSON);
        }
        return json;
    }

    private static List<DownMsg> getFileMsg(String json){
        List<DownMsg> downMsgList = new ArrayList<>();
        // TODO 校验json
        EPVideoVO epVideoVO = JSON.parseObject(json,EPVideoVO.class);
        int i = 0;
        for(EPVideoVO.ep ep: epVideoVO.getEpList()){
            String url = String.format(ConstantEPFlvURL.epUrlPlayurl,ep.getId(),ep.getAid(),ep.getBvid(),ep.getCid(),ConstantQuality.quality_1080_60);
            log.info("url:" + url);
            String realVideojson = iHttp.get(url,ConstantVideoFlvURL.GET, ConstantHeader.epFlv);

            log.info("realVideojson:" + realVideojson);
            String realFlvUrlurl = getRealFlvUrl(realVideojson);
            log.info("realFlvUrlurl:" + realFlvUrlurl);
            downMsg.setUrl(realFlvUrlurl);
            downMsg.setFilePath(ConstantDir.ep,ep.getTitle());
            // downMsg.setFilePath(ConstantDir.av,up,aid,ConstantDir.video);
            // TODO 可以手动格式化个格式 [AV][PART].flv
            downMsg.setFileName(ep.getTitle() + i++ + ConstantVideoFlvURL.downFileTypeFlv);
            downMsg.setType(ConstantVideoFlvURL.downFileUrlType);
            downMsg.setHeader(ConstantHeader.mapFlv);
            downMsgList.add(downMsg);
        }
        return downMsgList;
    }

    /**
     * 视频真实url
     * @param json
     * @return
     */
    private static String getRealFlvUrl(String json){
        com.alibaba.fastjson.JSONObject jsonObject = checkJson(json);
        // url在json中的位置
        com.alibaba.fastjson.JSONObject realUrlObj = (JSONObject)jsonObject.getJSONObject("result").getJSONArray("durl").get(0);
        String realUrl = realUrlObj.getString("url");
        return realUrl;
    }

    private static void saveHtml(String str,String ep,String type){
        downHTML.setContent(str);
        downHTML.setFilePath(ConstantDir.ep,ep);
        downHTML.setFileName(ep + type);
        down.saveFile(downHTML);
    }

    private static String readHtml(String ep,String type){
        downHTML.setFilePath(ConstantDir.ep,ep);
        downHTML.setFileName(ep + type);
        return down.readFile(downHTML);
    }

    public static void main(String[] args) {
        flow("ss26175");
    }

}
