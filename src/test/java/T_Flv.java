import c.c.bilibili.Bilibili_Flv;
import org.junit.Test;

/**
 * Yukino
 * 2020/3/6
 */
public class T_Flv {

    Bilibili_Flv bilibili_flv=new Bilibili_Flv("77019542");

    @Test
    public void downUp(){
        try {
            bilibili_flv.requestFlow();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void downAV(){
        try {
            bilibili_flv.downAV("78097482", true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
