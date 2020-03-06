package c.c.bilibili;

import c.c.entity.Bilibili_album;
import c.c.entity.Bilibili_album_pic;
import c.c.utils.Method_down;
import c.c.utils.Request_Method;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Yukino
 * 2020/3/3
 */
public class Bilibili_Album {

    public void requestFlow()throws Exception{
        Thread.sleep(1000);
        //先根据链接获取个数
        String countJson = Request_Method.js_commom(forCountUrl+poster_uid,"","POST");
        //得到了相册的个数
        String count = albumCount(countJson);
        System.out.println("一共有"+count+"个相册");
        String allImgJson = Request_Method.js_commom(setForAllImgUrl(poster_uid,count),"","POST");
        allImgUrl(allImgJson,true);
    }

    //参数 UP 的唯一id
    public Bilibili_Album(String poster_uid) {
        this.poster_uid = poster_uid;
    }

    // 指定up的id
    private  String poster_uid = "12";
    //获取相册总数的url
    private String forCountUrl = "https://api.vc.bilibili.com/link_draw/v1/doc/upload_count?uid=";
    /**
     *  获取所有相册的数据的地址
     * @param poster_uid
     * @param page_size
     * @return
     */
    public String setForAllImgUrl(String poster_uid,String page_size) {
        return "https://api.vc.bilibili.com/link_draw/v1/doc/doc_list?uid="+poster_uid+"&page_num=0&biz=all&page_size=" +page_size ;
    }

    List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
    public Map<String,List<Bilibili_album>> map_album = new HashMap<String,List<Bilibili_album>>();
    public Map<String,List<Bilibili_album_pic>> map_album_pic = new HashMap<String,List<Bilibili_album_pic>>();

    /**
     * 获取相簿总个数
     * @param json
     * @return
     */
    public  String albumCount(String json){
        System.out.println("albumCount:"+json);
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONObject jsonObjectDate = jsonObject.getJSONObject("data");
        String count = jsonObjectDate.getString("all_count");
        return count;
    }

    /**
     * 获取所有照片
     * @param json
     * @return
     * @throws Exception
     */
    public  Map<String,Object> allImgUrl(String json,Boolean down)throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        System.out.println("allImgUrl:"+json);
        Bilibili_album bilibili_album;
        Bilibili_album_pic bilibili_album_pic;
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONObject jsonObjectDate = jsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObjectDate.getJSONArray("items");

        List<Bilibili_album> list_album = new ArrayList<Bilibili_album>();
        List<Bilibili_album_pic> list_album_pic = new ArrayList<Bilibili_album_pic>();

        for(int i = 0 ;i<jsonArray.size();i++){
            bilibili_album = new Bilibili_album();

            JSONObject jsonObj = (JSONObject)jsonArray.get(i);
            bilibili_album.setAlbum(jsonObj.getString("doc_id"));
            bilibili_album.setPoster_uid(jsonObj.getString("poster_uid"));
            bilibili_album.setTitle(jsonObj.getString("title"));
            bilibili_album.setDescription(jsonObj.getString("description"));
            bilibili_album.setCount(jsonObj.getString("count"));
            bilibili_album.setCtime(jsonObj.getString("ctime"));
            bilibili_album.setView(jsonObj.getString("view"));
            bilibili_album.setLike(jsonObj.getString("like"));

            list_album.add(bilibili_album);
            JSONArray pic_array = jsonObj.getJSONArray("pictures");
            for(int j = 0 ; j < pic_array.size() ; j++ ){
                bilibili_album_pic = new Bilibili_album_pic();
                JSONObject json_pic = (JSONObject)pic_array.get(j);
                bilibili_album_pic.setDoc_id(jsonObj.getString("doc_id"));
                bilibili_album_pic.setPoster_uid(jsonObj.getString("poster_uid"));
                bilibili_album_pic.setImg_src(json_pic.getString("img_src"));

                if(down) {
                    //调用下载
                    Method_down.down(json_pic.getString("img_src"),"相册\\"+poster_uid);
                    //System.out.println("图片下载地址:"+json_pic.getString("img_src"));
                }
                System.out.println("图片下载地址:"+json_pic.getString("img_src"));

                bilibili_album_pic.setImg_width(json_pic.getString("img_width"));
                bilibili_album_pic.setImg_height(json_pic.getString("img_height"));
                bilibili_album_pic.setImg_size(json_pic.getString("img_size"));
                list_album_pic.add(bilibili_album_pic);

            }
        }
        map.put("map_album",list_album);
        map.put("map_album_pic",list_album_pic);

        return map;
    }

}
