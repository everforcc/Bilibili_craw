package cc.bilibili.impl;

import cc.bilibili.CheckInput;
import cc.bilibili.IVideo;
import cc.constant.ConstantHeader;
import cc.constant.ConstantVideoFlvURL;
import cc.entity.DownMsg;
import cc.utils.IHttp;
import cc.utils.impl.HttpUrlConnectionUtils;
import cc.vo.BVideoVO;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static cc.bilibili.impl.AVFlv.*;

/**
 * @author everforcc
 */
public class AVMP4 implements IVideo {

    private static final IHttp iHttp = new HttpUrlConnectionUtils();
    /**
     * 这个方法可以直接从av和cid拿到链接
     */

    public static void flow(String aid){
        aid = CheckInput.checkAV(aid);
        // 1. 根据aid获取cid json
        String cidJson = getCidJsonByAid(aid);
        // 2. cid转换VO
        BVideoVO bVideoVO = getCidVO(cidJson);
        // 3. 文件信息
        // 3.1 获取文件地址
        // 3.2 组织问价信息
        List<DownMsg> downMsgList = getFileMsg(bVideoVO);
        // 4. 下载文件,当前为单个下载，所有信息都有了，可以调整
        downFile(downMsgList);
    }

    private static List<DownMsg> getFileMsg(BVideoVO bVideoVO){
        List<DownMsg> downMsgList = new ArrayList<>();
        String aid = bVideoVO.getAid();
        String bvid = bVideoVO.getBvid();
        List<BVideoVO.CidVO> cidVOList =  bVideoVO.getPages();
        for(BVideoVO.CidVO cidVO:cidVOList) {
            DownMsg downMsg = new DownMsg();
            // 校验
            // 组装链接
            String urlPath = String.format(ConstantVideoFlvURL.aidCidToRealVideoUrl_720, aid, bvid, cidVO.getCid());
            // 请求
            String videoJson = iHttp.get(urlPath, ConstantVideoFlvURL.aidCidToRealVideoUrlType, ConstantHeader.map,ConstantVideoFlvURL.charset);
//            System.out.println("cidVO.getCid():" + cidVO.getCid());
            System.out.println("videoJson:" + videoJson);
            String realUrl = getRealFlvUrl(videoJson);

            // 组织文件信息
            downMsg.setUrl(realUrl);
            videoPath(bVideoVO,downMsg,aid);
            // downMsg.setFilePath(ConstantDir.av,up,aid,ConstantDir.video);
            // TODO 可以手动格式化个格式 [AV][PART].flv
            downMsg.setFileName(bVideoVO.getAid() + cidVO.getPart() + ConstantVideoFlvURL.downFileTypeMP4);
            downMsg.setType(ConstantVideoFlvURL.downFileUrlType);
            downMsg.setHeader(ConstantHeader.mapFlv);
            downMsgList.add(downMsg);
        }
        return downMsgList;
    }

    private static String getRealFlvUrl(String json){
        JSONObject jsonObject = checkJson(json);
        // url在json中的位置
        JSONObject realUrlObj = (JSONObject)jsonObject.getJSONObject("data").getJSONArray("durl").get(0);
        String realUrl = realUrlObj.getString("url");
        return realUrl;
    }

    public static void main(String[] args) {
        flow("");
    }

}
