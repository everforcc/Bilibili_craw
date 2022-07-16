package cc.busi.video.flow;

import cc.busi.IVideo;
import cc.busi.check.CheckReturn;
import cc.busi.video.constant.ConstantUPFileName;
import cc.busi.video.constant.ConstantVideoFlvURL;
import cc.busi.video.vo.BVideoVO;
import cc.constant.*;
import cc.entity.DownMsg;
import cc.utils.http.IHttp;
import cc.utils.http.impl.HttpUrlConnectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author everforcc
 */
@Slf4j
public class AVMP4 implements IVideo {

    private static final IHttp iHttp = new HttpUrlConnectionUtils();

    /**
     * 这个方法可以直接从av和cid拿到链接
     */

    public static void flow(String aid) {
/*        aid = CheckInput.checkAV(aid);
        // 1. 根据aid获取cid json
        String cidJson = getCidJsonByAid(aid);
        // 2. cid转换VO
        BVideoVO bVideoVO = getCidVO(cidJson);
        // 3. 文件信息
        // 3.1 获取文件地址
        // 3.2 组织问价信息
        List<DownMsg> downMsgList = getFileMsg(bVideoVO);
        // 4. 下载文件,当前为单个下载，所有信息都有了，可以调整
        downFile(downMsgList);*/
    }

    /**
     * 获取视频真实地址
     *
     * @param bVideoVO
     * @return
     */
    public List<DownMsg> getFileMsg(BVideoVO bVideoVO) {
        // 1. 组装下载信息
        List<DownMsg> downMsgList = new ArrayList<>();
        // 2. ac和bv id
        String aid = bVideoVO.getAid();
        String bvid = bVideoVO.getBvid();
        // 3. cid集合
        List<BVideoVO.CidVO> cidVOList = bVideoVO.getPages();
        String title = bVideoVO.getTitle();
        // 4. 便利cid下载
        for (BVideoVO.CidVO cidVO : cidVOList) {
            DownMsg downMsg = new DownMsg();
            // 设置番号
            downMsg.setAid("av" + aid);
            // 校验
            // 这个位置请求mp4地址的链接
            String urlPath = String.format(ConstantVideoFlvURL.aidCidToRealVideoUrl_720, aid, bvid, cidVO.getCid());
            // 请求获得 mp4的播放地址 的json
            String videoJson = iHttp.get(urlPath, ConstantReqType.GET, ConstantHeader.map, ConstantCharset.UTF_8);
            log.info("videoJson: 【{}】", videoJson);
            // 获得mp4的播放地址
            String realUrl = getRealFlvUrl(videoJson);
            log.info("realUrl: 【{}】", realUrl);
            // 视频文件真实地址
            downMsg.setUrl(realUrl);
            // 请求方式
            downMsg.setReqType(ConstantReqType.GET);
            // 文件路径
            String d2_up_idFormat = String.format(ConstantDir.d2_up_idFormat, bVideoVO.getOwner().getMid(), bVideoVO.getOwner().getName());
            downMsg.setFilePath(ConstantDir.d1_up, d2_up_idFormat, ConstantDir.d3_up_video, aid);
            // 文件名
            String fileName = String.format(ConstantUPFileName.d3_up_video_name, bVideoVO.getAid(), title, cidVO.getPart(), ConstantFile.MP4);
            downMsg.setFileName(fileName);

            // 请求头
            downMsg.setHeader(ConstantHeader.mapFlv);
            downMsgList.add(downMsg);
        }
        return downMsgList;
    }

    private static String getRealFlvUrl(String json) {
        // 格式化
        JSONObject jsonObject = JSON.parseObject(json);
        // 校验
        CheckReturn.checkJson(jsonObject);
        // url在json中的位置
        JSONObject realUrlObj = (JSONObject) jsonObject.getJSONObject("data").getJSONArray("durl").get(0);
        return realUrlObj.getString("url");
    }

}
