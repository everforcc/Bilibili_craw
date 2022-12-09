/**
 * @Description
 * @Author everforcc
 * @Date 2022-12-09 10:12
 * Copyright
 */

package cc.user;

import cc.controller.EPFLVController;
import cc.controller.VideoAVController;
import org.junit.Test;

/**
 * 用户使用的接口
 */
public class UserUseTests {

    /**
     * 下载 av 视频
     */
    VideoAVController videoAVController = new VideoAVController();

    /**
     * 电影或者连续剧
     */
    EPFLVController epflvController = new EPFLVController();

    /**
     * 视频封面
     */
    @Test
    public void avCover() {

    }

    /**
     * 视频 flv 格式
     */
    @Test
    public void avFlv() {
        String av = "BV";
        videoAVController.flv(av);
    }

    /**
     * 视频 mp4 格式
     */
    @Test
    public void avMp4() {
        String av = "BV";
        videoAVController.mp4(av);
    }

    /**
     * 一次下载一集
     */
    @Test
    public void epSingle() {
        String input = "ss4452";
        int index = 2;
        epflvController.dEp(input, index);
    }

    /**
     * 一次下载一个集合
     */
    @Test
    public void epList() {
        String input = "ss4452";
        int startIndex = 3;
        int endIndex = 25;
        epflvController.dEp(input, startIndex, endIndex);
    }

    /**
     * 下载所有视频，不建议，没有断点续下功能
     */
    @Test
    public void epAll() {

    }

}
