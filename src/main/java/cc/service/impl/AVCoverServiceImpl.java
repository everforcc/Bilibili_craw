/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 16:06
 * Copyright
 */

package cc.service.impl;

import cc.busi.cover.flow.impl.AVCover;
import cc.entity.DownMsg;
import cc.service.IAVCoverService;

public class AVCoverServiceImpl implements IAVCoverService {


    @Override
    public void downCover(String input) {
        try {
            // 封面对象
            AVCover avCover = new AVCover();

            // 1. 从 录入取出aid
            String aid = avCover.urlToAV(input);
            // 2. 从 aid取出对应html
            String html = avCover.htmlByAid(aid);
            // 3. 从 html取出图片信息
            String imgUrl = avCover.getImgUrl(html);
            // 4. 组装下载信息
            DownMsg downMsg = avCover.getDownMsg(imgUrl, aid, null);
            // 5. 下载文件
            avCover.downFile(downMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
