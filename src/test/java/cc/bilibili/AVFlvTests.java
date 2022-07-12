/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 09:39
 * Copyright
 */

package cc.bilibili;

import cc.busi.video.flow.AVFlv;
import org.junit.Test;

public class AVFlvTests {

    AVFlv iVideo = new AVFlv();

    /**
     * 测试url转换av
     */
    @Test
    public void urlToAV() {
        String input_null = "";
//        iVideo.urlToAV(input_null);

        String input_http = "https://www.bilibili.com/video/BV1UL411T7N8";
//        iVideo.urlToAV(input_http);

        String input_http_ = "https://www.bilibili.com/video/BV1UL411T7N8/";
//        iVideo.urlToAV(input_http_);

        String input_http_params = "https://www.bilibili.com/video/BV1UL411T7N8/?spm_id_from=autoNext&vd_source=aa60168015e9717c8293fc54bb25ab9b";
//        iVideo.urlToAV(input_http_params);

        // https://www.bilibili.com/video/av464269880
        String input_av = "av464269880";
//        iVideo.urlToAV(input_av);

        String input_av_without = "464269880";
//        iVideo.urlToAV(input_av_without);

        String input_bv = "BV1UL411T7N8";
        iVideo.urlToAV(input_bv);
    }

}
