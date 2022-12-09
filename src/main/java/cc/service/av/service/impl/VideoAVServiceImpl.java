/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 08:34
 * Copyright
 */

package cc.service.av.service.impl;

import cc.service.av.flow.impl.AVFlv;
import cc.service.av.flow.impl.AVMP4;
import cc.service.av.vo.BVideoVO;
import cc.utils.busi.DownMsg;
import cc.enums.VideoType;
import cc.service.av.service.IVideoAVService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 组装下载
 */
@Slf4j
public class VideoAVServiceImpl implements IVideoAVService {

    // 下载flv流程
    AVFlv iVideo = new AVFlv();

    // 下载mp4
    AVMP4 avmp4 = new AVMP4();

    @Override
    public void flv(String input, String constantQuality) {
        log.info("下载短视频 flv格式");

        // 1. 从用户录入获取av号
        String avCode = iVideo.urlToAV(input);

        // 2. 用av换cid
        String cidJson = iVideo.getCidJsonByAid(avCode);

        // 3. cid转 对象
        BVideoVO bVideoVO = iVideo.getCidVO(cidJson);

        // 4. 文件信息
        // 4.1 获取文件地址
        // 4.2 组织问价信息
        List<DownMsg> downMsgList = iVideo.getFileMsg(bVideoVO, constantQuality);

        // 5. 下载文件,当前为单个下载，所有信息都有了，可以调整
        iVideo.downVideo(downMsgList);

        if (downMsgList.size() == 0) {
            return;
        }
        String filePath = downMsgList.get(0).getFilePath();

        // 6. 下载封面
        iVideo.downCover(bVideoVO, filePath);

        // 7. 保存cid json
        iVideo.saveCidJson(bVideoVO, filePath);

        // 8. 保存视频真实地址 json
        iVideo.saveCidJson(downMsgList, filePath, bVideoVO.getAid(), VideoType.FLV);
    }

    /**
     * 加参数
     * mp4或flv,然后保存json和获取视频信息的时候判断一下
     */
    @Override
    public void mp4(String input) {
        log.info("下载短视频 mp4格式");

        // 1. 从用户录入获取av号
        String avCode = iVideo.urlToAV(input);

        // 2. 用av
        String cidJson = iVideo.getCidJsonByAid(avCode);

        // 3. cid转 对象
        BVideoVO bVideoVO = iVideo.getCidVO(cidJson);

        // 4. 文件信息
        // 4.1 获取文件地址
        // 4.2 组织问价信息
        List<DownMsg> downMsgList = avmp4.getFileMsg(bVideoVO);

        // 5. 下载文件,当前为单个下载，所有信息都有了，可以调整
        iVideo.downVideo(downMsgList);

        if (downMsgList.size() == 0) {
            return;
        }
        String filePath = downMsgList.get(0).getFilePath();

        // 6. 下载封面
        iVideo.downCover(bVideoVO, filePath);

        // 6. 保存json
        iVideo.saveCidJson(bVideoVO, filePath);

        // 8. 保存视频真实地址 json
        iVideo.saveCidJson(downMsgList, filePath, bVideoVO.getAid(), VideoType.MP4);
    }

    @Override
    public void flow(String input, String constantQuality, VideoType type) {
        log.info("下载短视频 flv格式");

        // 1. 从用户录入获取av号
        String avCode = iVideo.urlToAV(input);

        // 2. 用av换cid
        String cidJson = iVideo.getCidJsonByAid(avCode);

        // 3. cid转 对象
        BVideoVO bVideoVO = iVideo.getCidVO(cidJson);

        // 4. 文件信息
        // 4.1 获取文件地址
        // 4.2 组织问价信息
        List<DownMsg> downMsgList;
        if (VideoType.MP4 == type) {
            downMsgList = avmp4.getFileMsg(bVideoVO);
        } else {
            downMsgList = iVideo.getFileMsg(bVideoVO, constantQuality);
        }

        // 5. 下载文件,当前为单个下载，所有信息都有了，可以调整
        iVideo.downVideo(downMsgList);


        if (downMsgList.size() == 0) {
            return;
        }
        String filePath = downMsgList.get(0).getFilePath();

        // 6. 下载封面
        iVideo.downCover(bVideoVO, filePath);

        // 7. 保存cid json
        iVideo.saveCidJson(bVideoVO, filePath);

        // 8. 保存视频真实地址 json
        iVideo.saveCidJson(downMsgList, filePath, bVideoVO.getAid(), type);
    }

    @Override
    public void m4s() {

    }
}
