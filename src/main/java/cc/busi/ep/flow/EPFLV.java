package cc.busi.ep.flow;

import cc.busi.IVideo;
import cc.busi.check.CheckReturn;
import cc.busi.ep.constant.ConstantEPFlvURL;
import cc.busi.ep.dto.EPVideoVO;
import cc.constant.*;
import cc.entity.DownMsg;
import cc.enums.CodeExceptionEnum;
import cc.utils.file.IFileChar;
import cc.utils.http.IHttp;
import cc.utils.http.impl.HttpUrlConnectionUtils;
import cc.utils.http.impl.JsoupUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author everforcc 2021-10-09
 */
@Slf4j
public class EPFLV implements IVideo {

    private static IHttp iHttp = new JsoupUtils();

    private static IHttp down = new HttpUrlConnectionUtils();

    private static final String downSplit = "-down";

    /**
     * 2.
     *
     * @param inputEP
     * @return
     */
    public String getHTMLByep(String inputEP) {
        String ep = inputEP;
        String html;
        if (StringUtils.isNotBlank(html = readMsg(ep, ConstantFile.HTML))) {
            log.info("已存在，不重复读取");
            return html;
        }
        log.info(ep + "不存在，开始读取");
        // 不能为空
        CodeExceptionEnum.AID_NULL.error("".equals(ep));
        // 根据id请求必须有返回数据
        // 根据aid拿到cid的数据
        String urlPath = String.format(ConstantEPFlvURL.rootUrl, ep);
        html = iHttp.get(urlPath, ConstantReqType.GET, ConstantHeader.epFlv, inputEP);
        saveHtml(html, ep);
        return html;
    }

    /**
     * 3.
     *
     * @param html
     * @return
     */
    public String matchJSON(String ep, String html) {
        String json;
        if (StringUtils.isNotBlank(json = readMsg(ep, ConstantFile.JSON))) {
            log.info("JSON已存在，不重复读取");
            log.info("json:" + json);
            return json;
        }

        Pattern pattern = Pattern.compile(ConstantEPFlvURL.regex);
        Matcher matcher = pattern.matcher(html);
        //是否匹配到了 , 只能匹配一个
        int i = 0;
        if (matcher.find()) {
            json = matcher.group(1);
            log.info("json:" + json);
            saveJson(json, ep);
        }
        return json;
    }

    /**
     * 4.
     *
     * @param json
     * @return
     */
    public List<DownMsg> getFileMsg(String ep, String json) {

        List<DownMsg> downMsgList;

        if (Objects.nonNull(downMsgList = readDown(ep, ConstantFile.JSON))) {
            log.info("JSON已存在，不重复读取");
            log.info("json:" + downMsgList);
            return downMsgList;
        }
        downMsgList = new ArrayList<>();

        // TODO 校验json
        EPVideoVO epVideoVO = JSON.parseObject(json, EPVideoVO.class);
        String h1Title = epVideoVO.getH1Title();
        if (StringUtils.isEmpty(h1Title)) {
            throw new RuntimeException("h1Title 为空请调整");
        }
        String epName = h1Title.substring(0, h1Title.indexOf("："));

        int i = 0;
        for (EPVideoVO.ep epVideoEp : epVideoVO.getEpList()) {
            // 下载文件信息
            DownMsg downMsg = new DownMsg();
            String url = String.format(ConstantEPFlvURL.epUrlPlayurl, epVideoEp.getId(), epVideoEp.getAid(), epVideoEp.getBvid(), epVideoEp.getCid(), ConstantQuality.quality_1080_60);
            log.info("url:" + url);
            String realVideojson = iHttp.get(url, ConstantReqType.GET, ConstantHeader.epFlv, epVideoEp.getAid());

            log.info("realVideojson:" + realVideojson);
            String realFlvUrlurl = getRealFlvUrl(realVideojson);
            log.info("realFlvUrlurl:" + realFlvUrlurl);
            downMsg.setUrl(realFlvUrlurl);


            downMsg.setFilePath(ConstantDir.d1_ep, "ep" + epVideoEp.getId(), epName); //epVideoEp.getTitle(),
            // downMsg.setFilePath(ConstantDir.av,up,aid,ConstantDir.video);
            // TODO 可以手动格式化个格式 [AV][PART].flv
            downMsg.setFileName(epVideoEp.getTitleFormat() + ConstantFile.FLV);
            downMsg.setReqType(ConstantReqType.GET);
            downMsg.setHeader(ConstantHeader.mapFlv);
            downMsgList.add(downMsg);
        }
        saveDown(downMsgList, ep);
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

    /**
     * 保存html
     *
     * @param html html内容
     * @param ep   番号id
     *             文件后缀 html
     */
    private static void saveHtml(String html, String ep) {
        DownMsg downHTML = new DownMsg();
        downHTML.setContent(html);
        downHTML.setFilePath(ConstantDir.d1_ep, ep);
        downHTML.setFileName(ep + ConstantFile.HTML);
        IFileChar.saveStrToFile(downHTML);
    }

    /**
     * 保存 文件信息 json
     *
     * @param epListJson 视频列表json
     * @param ep         视频id
     *                   文件类型 json
     */
    private static void saveJson(String epListJson, String ep) {
        DownMsg downHTML = new DownMsg();
        JSONObject jsonObject = JSONObject.parseObject(epListJson);
        epListJson = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
        downHTML.setContent(epListJson);
        downHTML.setFilePath(ConstantDir.d1_ep, ep);
        downHTML.setFileName(ep + ConstantFile.JSON);
        IFileChar.saveStrToFile(downHTML);
    }

    /**
     * 保存down信息
     *
     * @param downMsgList 组织好的下载信息
     * @param ep
     */
    private static void saveDown(List<DownMsg> downMsgList, String ep) {
        //List<DownMsg> downMsgList = new ArrayList<>();
        DownMsg downHTML = new DownMsg();
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(downMsgList));
        String downJson = JSON.toJSONString(jsonArray, SerializerFeature.PrettyFormat);
        downHTML.setContent(downJson);
        downHTML.setFilePath(ConstantDir.d1_ep, ep);
        downHTML.setFileName(ep + downSplit + ConstantFile.JSON);
        IFileChar.saveStrToFile(downHTML);
    }

    /**
     * 读取文件信息
     *
     * @param ep   业务id
     * @param type 文件类型
     * @return 读取结果
     */
    private static String readMsg(String ep, String type) {
        DownMsg downHTML = new DownMsg();
        downHTML.setFilePath(ConstantDir.d1_ep, ep);
        downHTML.setFileName(ep + type);
        if (!IFileChar.exist(downHTML)) {
            return null;
        }
        return IFileChar.readFileToString(downHTML);
    }

    /**
     * 读取下载文件的信息
     *
     * @param ep   文件号
     * @param type 类型
     * @return
     */
    private static List<DownMsg> readDown(String ep, String type) {
        DownMsg downHTML = new DownMsg();
        downHTML.setFilePath(ConstantDir.d1_ep, ep);
        downHTML.setFileName(ep + downSplit + type);
        if (!IFileChar.exist(downHTML)) {
            return null;
        }
        String json = IFileChar.readFileToString(downHTML);

        return JSONArray.parseArray(json, DownMsg.class);
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

        //down.downFile(downMsgList.get(0));

        //downMsgList.forEach(iHttp::downFile);
        /*for(DownMsg downMsg:downMsgList){
            try {
                Method_down.downFlv(downMsg.getUrl(), "av36953163", "craw\\电影\\正片\\正片-4.flv", downMsg.getFileName(), ConstantVideoFlvURL.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

    public void downFile(List<DownMsg> downMsgList, int index) {
        // 地址，文件路径，文件名。type，headers
        int size = downMsgList.size();
        // 录入1说明看第一集,坐标为0
        if (index < 1 || index > size) {
            log.info("请正确录入第几集, 当前一共有 【{}】", size);
            return;
        }
        log.info("准备下载第 【{}】 集", index);
        index--;
        down.downFile(downMsgList.get(index));

    }

}
