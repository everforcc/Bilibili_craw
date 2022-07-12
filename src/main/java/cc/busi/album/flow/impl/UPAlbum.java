package cc.busi.album.flow.impl;

import cc.busi.album.dto.BAlbumVO;
import cc.busi.album.flow.IAlbum;
import cc.busi.check.CheckReturn;
import cc.constant.*;
import cc.entity.DownMsg;
import cc.utils.file.FileNameUtils;
import cc.utils.http.IHttp;
import cc.utils.http.impl.HttpUrlConnectionUtils;
import cc.utils.http.impl.JsoupUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author everforcc 2021-10-09
 */
@Slf4j
public class UPAlbum implements IAlbum {

    private static IHttp iHttp = new JsoupUtils();
    private static IHttp down = new HttpUrlConnectionUtils();

//    private static BAlbumVO bAlbumVO = new BAlbumVO();
//    private static DownMsg downMsg = new DownMsg();

    /**
     * 1. up下的相册信息
     *
     * @param upid up的uid
     */
    public BAlbumVO countByupid(String upid) {
        BAlbumVO bAlbumVO = new BAlbumVO();
        //String url = String.format(ConstantAlbum.doc_list,upid,"5");
        String url = String.format(ConstantAlbum.upload_count, upid);
        String json = iHttp.get(url, ConstantVideoFlvURL.GET, ConstantHeader.webJSON);
        log.info(json);

        // 格式化
        JSONObject jsonObject = JSON.parseObject(json);
        // 校验
        CheckReturn.checkJson(jsonObject);

        String count = jsonObject.getString("data");
        BAlbumVO.Count bAlbumVOCount = JSONObject.parseObject(count, BAlbumVO.Count.class);
        bAlbumVO.setUpid(upid);
        bAlbumVO.setCount(bAlbumVOCount);
        return bAlbumVO;
    }

    /**
     * 2. 获取相册集合
     *
     * @param bAlbumVO 相册信息
     */
    public void allMsg(BAlbumVO bAlbumVO) {
        String url = String.format(ConstantAlbum.doc_list, bAlbumVO.getUpid(), bAlbumVO.getCount().getAll_count());
        String json = iHttp.get(url, ConstantVideoFlvURL.GET, ConstantHeader.webJSONCookie);
        log.info(json);

        // 格式化
        JSONObject jsonObject = JSON.parseObject(json);
        // 校验
        CheckReturn.checkJson(jsonObject);

        String doc_list = jsonObject.getJSONObject("data").getString("items");
        List<BAlbumVO.Doc> docList = JSONObject.parseArray(doc_list, BAlbumVO.Doc.class);
        bAlbumVO.setDocList(docList);
    }

    /**
     * 3.
     *
     * @param bAlbumVO
     */
    public void downMsg(BAlbumVO bAlbumVO) {
        String upid = bAlbumVO.getUpid();
        List<BAlbumVO.Doc> docList = bAlbumVO.getDocList();
        for (BAlbumVO.Doc doc : docList) {
            int i = 0;
            for (BAlbumVO.Doc.Picture pic : doc.getPictures()) {
                DownMsg downMsg = new DownMsg();
                downMsg.setUrl(pic.getImg_src());

                downMsg.setFilePath(upid, ConstantDir.album, doc.getDoc_id());
                // 后缀名可以截取得到
                downMsg.setFileName(doc.getTitle() + i++ + FileNameUtils.subFileSuffix(pic.getImg_src()));
                downMsg.setHeader(ConstantHeader.web);
                downMsg.setType(ConstantVideoFlvURL.GET);

                downFile(downMsg);
            }
        }
    }

    /**
     * 下载文件
     *
     * @param downMsg 文件信息
     */
    private static void downFile(DownMsg downMsg) {
        down.downFile(downMsg);
    }

    /**
     * 保存下载的json
     *
     * @param bAlbumVO
     */
    public void saveJson(BAlbumVO bAlbumVO) {
        DownMsg downMsg = new DownMsg();
        downMsg.setContent(bAlbumVO.toString());
        downMsg.setFilePath(bAlbumVO.getUpid(), ConstantDir.album);
        // 后缀名可以截取得到
        downMsg.setFileName(ConstantDir.album + ConstantCommon.JSON);
        down.saveFile(downMsg);
    }

}
