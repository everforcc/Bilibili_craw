package c.c.bilibili;

import c.c.utils.Constant;
import c.c.utils.ConstantURL;
import c.c.utils.Quality;

public class Bilibili_Down {

    public static String qualityStr = Constant.quality_360;

    public static void downVideo(String id){
        down(id, qualityStr);
    }

    public static void downVideo(String id, Quality quality){
        qualityStr = Constant.quality_360;
        switch (quality){
            case quality_360:
                qualityStr = Constant.quality_360;
                break;
            case quality_480:
                qualityStr = Constant.quality_480;
                break;
            case quality_720:
                qualityStr = Constant.quality_720;
                break;
            case quality_1080:
                qualityStr = Constant.quality_1080;
                break;
            case quality_1080_up:
                qualityStr = Constant.quality_1080_up;
                break;
            case quality_1080_60:
                qualityStr = Constant.quality_1080_60;
                break;
        }
        down(id, qualityStr);
    }

    private static void down(String id,String quality){
        ConstantURL.quality = quality;
        // 处理数据
        if(id!=null){
            if(id.startsWith("ep")||id.startsWith("ss")){
                Bilibili_ep_Flv.epFlow(id);
            }else if(id.startsWith("BV")||id.startsWith("av")||id.startsWith("AV")){
                Bilibili_Video_Flv.downAV(id);
            }else{ // 判断是up主的ip
                // Bilibili_Video_Flv bilibili_video_flv = new Bilibili_Video_Flv("Bilibili_Video_Flv");
                Bilibili_Video_Flv.requestFlow(id);
            }
        }else {
            // 报错
        }
    }


}
