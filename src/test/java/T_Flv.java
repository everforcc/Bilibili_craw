import c.c.bilibili.Bilibili_Flv;
import org.junit.Test;

/**
 * Yukino
 * 2020/3/6
 */
public class T_Flv {

    Bilibili_Flv bilibili_flv=new Bilibili_Flv("437316738");
    //Bilibili_Flv bilibili_flv=new Bilibili_Flv();

    @Test
    public void downUp(){
        try {
            Bilibili_Flv bilibili_flv=new Bilibili_Flv("437316738");
            bilibili_flv.requestFlow();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void downAV(){
        try {
            bilibili_flv.downAV("84864707", true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
