package c.c;

import c.c.bilibili.Bilibili_Cover;
import org.junit.Test;
public class T_Cover {
    @Test
    public void a1(){
        try {
            Bilibili_Cover.getImgByAV("BV1CV411z7z8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
