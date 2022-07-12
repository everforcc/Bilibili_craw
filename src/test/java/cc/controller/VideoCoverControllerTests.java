/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 16:20
 * Copyright
 */

package cc.controller;

import org.junit.Test;

public class VideoCoverControllerTests {

    VideoCoverController videoCoverController = new VideoCoverController();

    @Test
    public void dCover() {
        String avCode = "https://www.bilibili.com/video/BV13U4y1D7ju?spm_id_from=333.999.0.0&vd_source=aa60168015e9717c8293fc54bb25ab9b";
        videoCoverController.dCover(avCode);
    }

}
