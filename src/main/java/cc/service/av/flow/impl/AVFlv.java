package cc.service.av.flow.impl;

import cc.utils.busi.CheckInput;
import cc.utils.busi.CheckReturn;
import cc.service.av.constant.ConstantUPFileName;
import cc.service.av.constant.ConstantVideoFlvURL;
import cc.service.av.flow.IVideo;
import cc.service.av.vo.BVideoVO;
import cc.constant.*;
import cc.utils.busi.DownMsg;
import cc.enums.VideoType;
import cc.utils.file.IFileChar;
import cc.utils.http.IHttp;
import cc.utils.http.impl.HttpUrlConnectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
@Slf4j
public class AVFlv implements IVideo {

    // 请求工具
    private static final IHttp iHttp = new HttpUrlConnectionUtils();

    /* 保存json等信息 */
    //private IFileByte iFileByte = new InputStreamUtils();

    /**
     * 1. 从用户录入转为AV号
     *
     * @param input 用户录入
     * @return 返回av号
     */
    public String urlToAV(String input) {
        log.info("输入aid 【{}】", input);
        String avCode = CheckInput.checkAV(input);
        log.info("返回aid 【{}】", avCode);
        return avCode;
    }

    /**
     * 2. 根据aid获取cid json
     *
     * @param aid av号
     * @return 返回cidjson
     */
    public String getCidJsonByAid(String aid) {
        // 根据id请求必须有返回数据
        // 根据aid拿到cid的数据
        String urlPath = String.format(ConstantVideoFlvURL.aidToCid, aid);
        String cidJson = iHttp.get(urlPath, ConstantReqType.GET, ConstantHeader.map, ConstantCharset.UTF_8);
        // 校验返回的json
        // CheckReturn.checkJson(cidJson);
        return cidJson;
    }

    /**
     * 3. cid转换VO
     *
     * @param json json信息
     * @return 返回格式化的对象
     */
    public BVideoVO getCidVO(String json) {
        // 格式化
        JSONObject jsonObject = JSON.parseObject(json);
        // 校验
        CheckReturn.checkJson(jsonObject);
        // 校验过后取出需要的数据
        String bVideoString = jsonObject.getString("data");
        // 转换VO
        return JSONObject.parseObject(bVideoString, BVideoVO.class);
    }

    /**
     * 因为每个cid对应一个视频所以要在方法内组装好信息
     * 4. 文件信息
     * 4.1 获取文件地址
     * 4.2 组织文件信息
     *
     * @param bVideoVO 文件基础信息
     * @return 文件下载视频信息
     */
    public List<DownMsg> getFileMsg(BVideoVO bVideoVO, String constantQuality) {
        // 1. 组装下载信息
        List<DownMsg> downMsgList = new ArrayList<>();
        // 2. aid
        String aid = bVideoVO.getAid();
        // 3. cid集合
        List<BVideoVO.CidVO> cidVOList = bVideoVO.getPages();
        String title = bVideoVO.getTitle();
        // 4. 便利 cid下载
        for (BVideoVO.CidVO cidVO : cidVOList) {
            DownMsg downMsg = new DownMsg();
            // 设置番号
            downMsg.setAid("av" + aid);
            // 组装cid链接
            String urlPath = String.format(ConstantVideoFlvURL.aidCidToRealVideoUrl, aid, cidVO.getCid(), constantQuality);
            // 请求获取视频真实地址
            String videoJson = iHttp.get(urlPath, ConstantReqType.GET, ConstantHeader.map, ConstantCharset.UTF_8);
            // 解析json视频真实地址
            String realUrl = getRealFlvUrl(videoJson);
            // 视频文件真实地址
            downMsg.setUrl(realUrl);
            // 链接请求方式
            downMsg.setReqType(ConstantReqType.GET);
            // 文件路径
            String d2_up_idFormat = String.format(ConstantDir.d2_up_idFormat, bVideoVO.getOwner().getMid(), bVideoVO.getOwner().getName());
            downMsg.setFilePath(ConstantDir.d1_up, d2_up_idFormat, ConstantDir.d3_up_video, title);
            // 文件名
            String fileName = String.format(ConstantUPFileName.d3_up_video_name, bVideoVO.getAid(), cidVO.getPart(), ConstantFile.FLV);
            downMsg.setFileName(fileName);
            // 请求头
            downMsg.setHeader(ConstantHeader.mapFlv);
            downMsgList.add(downMsg);
        }
        return downMsgList;
    }

    /**
     * 4.3 视频真实url
     *
     * @param json 视频地址的json
     * @return 视频地址
     */
    private static String getRealFlvUrl(String json) {
        // 格式化
        JSONObject jsonObject = JSON.parseObject(json);
        // 校验
        CheckReturn.checkJson(jsonObject);
        // url在json中的位置
        JSONObject realUrlObj = (JSONObject) jsonObject.getJSONObject("data").getJSONArray("durl").get(0);
        String realUrl = realUrlObj.getString("url");
        return realUrl;
    }

    /**
     * 5. 下载文件
     *
     * @param downMsgList 下载文件信息
     */
    public void downVideo(List<DownMsg> downMsgList) {
        for (DownMsg downMsg : downMsgList) {
            try {
                //iFileByte.downFlv(downMsg);
                iHttp.downFile(downMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 6. 下载封面
     *
     * @param bVideoVO 封面地址
     * @param filePath 封面路径
     */
    public void downCover(BVideoVO bVideoVO, String filePath) {
        String d3_up_video_cover_name = String.format(ConstantUPFileName.d3_up_video_cover_name, bVideoVO.getAid(), ConstantFile.JPG);

        DownMsg downMsg = new DownMsg();
        downMsg.setFilePath(filePath);
        downMsg.setFileName(d3_up_video_cover_name);
        downMsg.setUrl(bVideoVO.getPic());
        downMsg.setReqType(ConstantReqType.GET);

        //iFileByte.downByUrl(bVideoVO.getPic(), filePath, d3_up_video_cover_name);
        iHttp.downFile(downMsg);
    }

    /**
     * 7. 保存cid_json信息
     *
     * @param bVideoVO 视频信息
     * @param filePath 下载信息
     */
    public void saveCidJson(BVideoVO bVideoVO, String filePath) {
        DownMsg downMsg = new DownMsg();
        downMsg.setContent(bVideoVO.toString());
        downMsg.setFilePath(filePath);
        // 后缀名固定为json
        String d3_up_video_cid_json_name = String.format(ConstantUPFileName.d3_up_video_cid_json_name, bVideoVO.getAid(), ConstantFile.JSON);
        downMsg.setFileName(d3_up_video_cid_json_name);
        // 如果不存在就保存
        if (!IFileChar.fileExist(downMsg)) {
            IFileChar.saveStrToFile(downMsg);
        }
    }

    /**
     * 8. 保存视频真实地址_json信息
     *
     * @param downMsgList 视频真实地址信息
     * @param filePath    下载信息
     */
    public void saveCidJson(List<DownMsg> downMsgList, String filePath, String aid, VideoType type) {
        DownMsg downMsg = new DownMsg();

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(downMsgList));
        String content = JSONObject.toJSONString(jsonArray, SerializerFeature.PrettyFormat);

        downMsg.setContent(content);
        downMsg.setFilePath(filePath);
        // 后缀名固定为json
        String d3_up_video_cid_json_name = String.format(ConstantUPFileName.d3_up_video_real_json_name, aid, type, ConstantFile.JSON);
        downMsg.setFileName(d3_up_video_cid_json_name);
        // 如果不存在就保存
        //if (!IFileChar.exist(downMsg)) {
        IFileChar.saveStrToFile(downMsg);
        //}
    }


}
