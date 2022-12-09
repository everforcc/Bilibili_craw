/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 08:34
 * Copyright
 */

package cc.controller;

import cc.constant.ConstantQuality;
import cc.enums.VideoType;
import cc.service.av.service.IVideoAVService;
import cc.service.av.service.impl.VideoAVServiceImpl;

/**
 * up，视频o
 */
public class VideoAVController {

    IVideoAVService iVideoAVService = new VideoAVServiceImpl();

    /**
     * 下载 flv 的视频
     */
    public void flv(String input) {
        iVideoAVService.flv(input, ConstantQuality.quality_1080);
    }

    /**
     * 下载 mp4 的视频
     */
    public void mp4(String input) {
        iVideoAVService.mp4(input);
    }

    public void flow(String input, VideoType videoType) {
        iVideoAVService.flow(input, ConstantQuality.quality_1080, videoType);
    }

    /**
     * 下载 m4s 的视频
     */
    public void m4s() {

    }

}
