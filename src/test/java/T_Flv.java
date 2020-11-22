import c.c.bilibili.Bilibili_Down;
import c.c.utils.Quality;
import org.junit.Test;


/**
 * Yukino
 * 2020/3/6
 */
public class T_Flv {

    /*
    1.视频的av号 起名字的时候的av的问题
    2. 莫名其妙乱码的问题
    3.选择清晰度 enum 和集数的问题，给个重构默认全部
    4. 代码太乱了，整理
    5. 视频下载请求连接的地方写死了，需要调整
    ---
    6. 视频太多的分页问题，例如柯南

    命名有点问题
    登录没解决
    */

    @Test
    public void t1(){
        // 支持bv ，av ， ss ep up主的号
        /*Bilibili_Down.downVideo("BV1ti4y1u7wR", Quality.quality_360);
        Bilibili_Down.downVideo("BV1ti4y1u7wR", Quality.quality_480);
        Bilibili_Down.downVideo("BV1ti4y1u7wR", Quality.quality_720);
        Bilibili_Down.downVideo("BV1ti4y1u7wR", Quality.quality_1080);
        Bilibili_Down.downVideo("BV1ti4y1u7wR", Quality.quality_1080_up);*/
        //Bilibili_Down.downVideo("ss26175", Quality.quality_1080_60);

        Bilibili_Down.downVideo("BV1Ny4y167Uj", Quality.quality_1080_60);
    }


}

