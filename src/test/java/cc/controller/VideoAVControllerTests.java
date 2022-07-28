/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 16:03
 * Copyright
 */

package cc.controller;

import cc.enums.VideoType;
import org.junit.Test;

public class VideoAVControllerTests {

    VideoAVController videoAVController = new VideoAVController();

    /**
     * 下载 flv 文件
     */
    @Test
    public void dAVFlv() {
        String avCode_77 = "BV1tB4y1Y7s2";
        String avCode = "BV1ST4y1r79B";
        videoAVController.flv(avCode_77);
    }

    /**
     * 下载 mp4 文件
     */
    @Test
    public void dAVMP4() {
        String avCode = "BV1tB4y1Y7s2";
        videoAVController.mp4(avCode);
    }

    @Test
    public void flow() {
        String avCode = "BV1tB4y1Y7s2";
        videoAVController.flow(avCode, VideoType.MP4);
    }

}
