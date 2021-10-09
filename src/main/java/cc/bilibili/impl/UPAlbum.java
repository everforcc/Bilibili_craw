package cc.bilibili.impl;

import cc.bilibili.IAlbum;
import cc.constant.*;
import cc.entity.DownMsg;
import cc.utils.FileUtils;
import cc.utils.IHttp;
import cc.utils.impl.HttpUrlConnectionUtils;
import cc.utils.impl.JsoupUtils;
import cc.vo.BAlbumVO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import static cc.bilibili.impl.AVFlv.checkJson;
import static cc.bilibili.impl.AVFlv.videoPath;

/**
 * @author everforcc 2021-10-09
 */
@Slf4j
public class UPAlbum implements IAlbum {
    private static BAlbumVO bAlbumVO = new BAlbumVO();

    private static IHttp iHttp = new JsoupUtils();
    private static IHttp down = new HttpUrlConnectionUtils();

    private static DownMsg downMsg = new DownMsg();
    private static void countByupid(String upid){
        //String url = String.format(ConstantAlbum.doc_list,upid,"5");
        String url = String.format(ConstantAlbum.upload_count,upid);
        String json = iHttp.get(url,ConstantVideoFlvURL.GET, ConstantHeader.webJSON);
        log.info(json);
        JSONObject jsonObject = checkJson(json);

        String count = jsonObject.getString("data");
        BAlbumVO.Count bAlbumVOCount = JSONObject.parseObject(count,BAlbumVO.Count.class);
        bAlbumVO.setUpid(upid);
        bAlbumVO.setCount(bAlbumVOCount);
    }

    private static void allMsg(){
        String url = String.format(ConstantAlbum.doc_list,bAlbumVO.getUpid(),bAlbumVO.getCount().getAll_count());
        String json = iHttp.get(url,ConstantVideoFlvURL.GET, ConstantHeader.webJSONCookie);
        log.info(json);
        JSONObject jsonObject = checkJson(json);

        String doc_list = jsonObject.getJSONObject("data").getString("items");
        List<BAlbumVO.Doc> docList = JSONObject.parseArray(doc_list,BAlbumVO.Doc.class);
        bAlbumVO.setDocList(docList);
    }

    public static void downMsg(){
        String upid = bAlbumVO.getUpid();
        List<BAlbumVO.Doc> docList = bAlbumVO.getDocList();
        for(BAlbumVO.Doc doc:docList) {
            int i = 0;
            for(BAlbumVO.Doc.Picture pic:doc.getPictures()) {
                downMsg.setUrl(pic.getImg_src());

                downMsg.setFilePath(upid,ConstantDir.album,doc.getDoc_id());
                // 后缀名可以截取得到
                downMsg.setFileName(doc.getTitle() + i++ + FileUtils.subFileSuffix(pic.getImg_src()));
                downMsg.setHeader(ConstantHeader.web);
                downMsg.setType(ConstantVideoFlvURL.GET);

                downFile(downMsg);
            }
        }
    }

    static void downFile(DownMsg downMsg){
        down.downFile(downMsg);
    }

    private static void saveJson(){
        downMsg.setContent(bAlbumVO.toString());
        downMsg.setFilePath(bAlbumVO.getUpid(),ConstantDir.album);
        // 后缀名可以截取得到
        downMsg.setFileName(ConstantDir.album + Constant.JSON);
        down.saveFile(downMsg);
    }

    public static void flow(String upid){
        countByupid(upid);
        allMsg();
        downMsg();
        saveJson();
        System.out.println(bAlbumVO.toString());
    }


    public static void main(String[] args) {
        flow("");
    }

}
