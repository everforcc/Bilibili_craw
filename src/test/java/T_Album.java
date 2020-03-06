import c.c.bilibili.Bilibili_Album;
import org.junit.Test;

/**
 * Yukino
 * 2020/3/6
 */
public class T_Album {
    //参数up id
    Bilibili_Album bilibili_album = new Bilibili_Album("77019542");
    @Test
    public void donwUP(){
        try {
            bilibili_album.requestFlow();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
