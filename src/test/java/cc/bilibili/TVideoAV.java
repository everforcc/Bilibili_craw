package cc.bilibili;

import cc.bilibili.impl.AVFlv;
import cc.constant.ConstantQuality;
import org.junit.Test;

/**
 * @author everforcc 2021-10-09
 */
public class TVideoAV {

    @Test
    public void f(){
        AVFlv.flow("5912713", ConstantQuality.quality_1080);
    }

}
