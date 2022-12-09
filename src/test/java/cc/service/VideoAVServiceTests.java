/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 09:06
 * Copyright
 */

package cc.service;

import cc.constant.ConstantQuality;
import cc.service.av.service.IVideoAVService;
import cc.service.av.service.impl.VideoAVServiceImpl;
import org.junit.Test;

public class VideoAVServiceTests {
    IVideoAVService iVideoAVService = new VideoAVServiceImpl();

    @Test
    public void fAVFlv() {
        String input_null  = "";
//        iVideoAVService.flv(input_null);

        String input_http  = "https://www.bilibili.com/video/BV1UL411T7N8";
//        iVideoAVService.flv(input_http);

        String input_http_ = "https://www.bilibili.com/video/BV1UL411T7N8/";
//        iVideoAVService.flv(input_http_);

        String input_http_params = "https://www.bilibili.com/video/BV15v4y1T7Hx?spm_id_from=333.999.0.0";
        iVideoAVService.flv(input_http_params, ConstantQuality.quality_1080);

        // https://www.bilibili.com/video/av464269880
        String input_av = "av464269880";
//        iVideoAVService.flv(input_av);

        String input_av_without = "av464269880";
//        iVideoAVService.flv(input_av_without, ConstantQuality.quality_1080);

        String input_bv = "BV1sB4y1y7qg";
//        iVideoAVService.flv(input_bv, ConstantQuality.quality_1080);
    }

    @Test
    public void fAVMP4() {

        String input_http  = "https://www.bilibili.com/video/BV12v411j7kf?spm_id_from=333.999.0.0";
        iVideoAVService.mp4(input_http);

    }

}
