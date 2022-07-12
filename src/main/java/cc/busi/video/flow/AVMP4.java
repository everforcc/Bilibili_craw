package cc.busi.video.flow;

import cc.busi.IVideo;
import cc.busi.check.CheckReturn;
import cc.busi.video.vo.BVideoVO;
import cc.constant.ConstantDir;
import cc.constant.ConstantHeader;
import cc.constant.ConstantVideoFlvURL;
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

        // 4. 便利cid下载
        for (BVideoVO.CidVO cidVO : cidVOList) {
            DownMsg downMsg = new DownMsg();
            // 设置番号
            downMsg.setAid(aid);
            // 校验
            // 这个位置请求mp4地址的链接
            String urlPath = String.format(ConstantVideoFlvURL.aidCidToRealVideoUrl_720, aid, bvid, cidVO.getCid());
            // 请求获得 mp4的播放地址 的json
            String videoJson = iHttp.get(urlPath, ConstantVideoFlvURL.aidCidToRealVideoUrlType, ConstantHeader.map, ConstantVideoFlvURL.charset);
            log.info("videoJson: 【{}】", videoJson);
            // 获得mp4的播放地址
            String realUrl = getRealFlvUrl(videoJson);
            log.info("realUrl: 【{}】", realUrl);
            // 组织文件信息
            downMsg.setUrl(realUrl);

            String up = String.format(ConstantDir.up, bVideoVO.getOwner().getMid(), bVideoVO.getOwner().getName());
            downMsg.setFilePath(up, ConstantDir.av_mp4, aid);

            downMsg.setFileName(bVideoVO.getAid() + cidVO.getPart() + ConstantVideoFlvURL.downFileTypeMP4);
            downMsg.setType(ConstantVideoFlvURL.downFileTypeMP4);
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
