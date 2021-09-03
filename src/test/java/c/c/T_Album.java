package c.c;

import c.c.bilibili.Bilibili_Album;
import org.junit.Test;

/**
 * Yukino
 * 2020/3/6
 */
public class T_Album {
    //参数up id
    Bilibili_Album bilibili_album = new Bilibili_Album("6591412");
    @Test
    public void donwUP(){
        try {
            // 再加个相册的分类和 标题
            bilibili_album.requestFlow();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //参数up id
    Bilibili_Album bilibili_album2 = new Bilibili_Album("23400436");
    @Test
    public void donwUP2(){
        try {
            // 再加个相册的分类和 标题
            bilibili_album2.requestFlow();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
