package cc.bilibili;

import cc.bilibili.impl.*;
import cc.constant.ConstantQuality;
import org.junit.Test;

/**
 * @author everforcc 2021-10-09
 */
public class Tmenu {

    /**
     * 所有功能都放到这里
     */

    @Test
    public void fCover(){
        // 封面 av
        AVCover.flow("5912713");
    }

    @Test
    public void fAVFlv(){
        // flv av
        AVFlv.flow("5912713", ConstantQuality.quality_1080);
    }

    @Test
    public void fAVMp4(){
        // mp4 av
        AVMP4.flow("5912713");
    }

    @Test
    public void fUPAlbum(){
        // upid album
        UPAlbum.flow("6591412");
    }

    @Test
    public void fep(){
        // ep
        EPFLV.flow("ss26175");
    }

}
