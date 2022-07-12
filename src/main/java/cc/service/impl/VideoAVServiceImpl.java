/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 08:34
 * Copyright
 */

package cc.service.impl;

import cc.busi.video.flow.AVFlv;
import cc.busi.video.flow.AVMP4;
import cc.entity.DownMsg;
import cc.service.IVideoAVService;
import cc.busi.video.vo.BVideoVO;
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

        // 2. 用av
        String cidJson = iVideo.getCidJsonByAid(avCode);

        // 3. cid转 对象
        BVideoVO bVideoVO = iVideo.getCidVO(cidJson);

        // 4. 文件信息
        // 4.1 获取文件地址
        // 4.2 组织问价信息
        List<DownMsg> downMsgList = iVideo.getFileMsg(bVideoVO, constantQuality);

        // 5. 下载文件,当前为单个下载，所有信息都有了，可以调整
        iVideo.downFile(downMsgList);

        if (downMsgList.size() == 0){
            return;
        }
        String filePath = downMsgList.get(0).getFilePath();
        // 6. 保存json
        iVideo.saveJson(bVideoVO, filePath);
    }

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
        iVideo.downFile(downMsgList);

        if (downMsgList.size() == 0){
            return;
        }
        String filePath = downMsgList.get(0).getFilePath();
        // 6. 保存json
        iVideo.saveJson(bVideoVO, filePath);
    }

    @Override
    public void m4s() {

    }
}
