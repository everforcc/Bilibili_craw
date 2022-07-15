package cc.busi.video.flow;

import cc.busi.IVideo;
import cc.busi.check.CheckInput;
import cc.busi.check.CheckReturn;
import cc.busi.video.vo.BVideoVO;
import cc.constant.ConstantCommon;
import cc.constant.ConstantDir;
import cc.constant.ConstantHeader;
import cc.constant.ConstantVideoFlvURL;
import cc.entity.DownMsg;
import cc.utils.file.IFileByte;
import cc.utils.file.IFileChar;
import cc.utils.file.impl.InputStreamUtils;
import cc.utils.http.IHttp;
import cc.utils.http.impl.HttpUrlConnectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
@Slf4j
public class AVFlv implements IVideo {

    // 请求工具
    private static final IHttp iHttp = new HttpUrlConnectionUtils();

    private IFileByte iFileByte = new InputStreamUtils();

    /**
     * 1. 从用户录入转为AV号
     *
     * @param input 用户录入
     * @return 返回av号
     */
    public String urlToAV(String input) {
        log.info("输入aid 【{}】", input);
        String avCode = CheckInput.checkAV(input);
        log.info("返回aid 【{}】", avCode);
        return avCode;
    }

    /**
     * 2. 根据aid获取cid json
     *
     * @param aid av号
     * @return 返回cidjson
     */
    public String getCidJsonByAid(String aid) {
        // 根据id请求必须有返回数据
        // 根据aid拿到cid的数据
        String urlPath = String.format(ConstantVideoFlvURL.aidToCid, aid);
        String cidJson = iHttp.get(urlPath, ConstantVideoFlvURL.aidToCidType, ConstantHeader.map, ConstantVideoFlvURL.charset);
        // 校验返回的json
        CheckReturn.checkJson(cidJson);
        return cidJson;
    }

    /**
     * 3. cid转换VO
     *
     * @param json json信息
     * @return 返回格式化的对象
     */
    public BVideoVO getCidVO(String json) {
        // 格式化
        JSONObject jsonObject = JSON.parseObject(json);
        // 校验
        // CheckReturn.checkJson(jsonObject);
        // 校验过后取出需要的数据
        String bVideoString = jsonObject.getString("data");
        // 转换VO
        return JSONObject.parseObject(bVideoString, BVideoVO.class);
    }

    /**
     * 因为每个cid对应一个视频所以要在方法内组装好信息
     * 4. 文件信息
     * 4.1 获取文件地址
     * 4.2 组织文件信息
     *
     * @param bVideoVO 文件基础信息
     * @return 文件下载视频信息
     */
    public List<DownMsg> getFileMsg(BVideoVO bVideoVO, String constantQuality) {
        // 1. 组装下载信息
        List<DownMsg> downMsgList = new ArrayList<>();
        // 2. aid
        String aid = bVideoVO.getAid();
        // 3. cid集合
        List<BVideoVO.CidVO> cidVOList = bVideoVO.getPages();

        // 4. 便利cid下载
        for (BVideoVO.CidVO cidVO : cidVOList) {
            DownMsg downMsg = new DownMsg();
            // 设置番号
            downMsg.setAid("av" + aid);
            // 校验
            // 组装cid链接
            String urlPath = String.format(ConstantVideoFlvURL.aidCidToRealVideoUrl, aid, cidVO.getCid(), constantQuality);
            // 请求
            String videoJson = iHttp.get(urlPath, ConstantVideoFlvURL.aidCidToRealVideoUrlType, ConstantHeader.map, ConstantVideoFlvURL.charset);
            // 获取视频真实地址
            String realUrl = getRealFlvUrl(videoJson);

            // 组织文件信息
            // 文件地址
            downMsg.setUrl(realUrl);
            // 链接请求方式
            downMsg.setReqType(ConstantVideoFlvURL.downFileUrlType);
            //videoPath(bVideoVO, downMsg, aid);
            // 文件路径
            String up = String.format(ConstantDir.up, bVideoVO.getOwner().getMid(), bVideoVO.getOwner().getName());
            downMsg.setFilePath(up, ConstantDir.av_flv, aid);

            // 文件名
            String fileName = String.format(ConstantDir.upFileName, bVideoVO.getAid(), cidVO.getPart(), ConstantVideoFlvURL.downFileTypeFlv);
            downMsg.setFileName(fileName);

            // 请求头
            downMsg.setHeader(ConstantHeader.mapFlv);
            downMsgList.add(downMsg);
        }
        return downMsgList;
    }

    /**
     * 4.3 视频真实url
     *
     * @param json 视频地址的json
     * @return 视频地址
     */
    private static String getRealFlvUrl(String json) {
        // 格式化
        JSONObject jsonObject = JSON.parseObject(json);
        // 校验
        CheckReturn.checkJson(jsonObject);
        // url在json中的位置
        JSONObject realUrlObj = (JSONObject) jsonObject.getJSONObject("data").getJSONArray("durl").get(0);
        String realUrl = realUrlObj.getString("url");
        return realUrl;
    }

    /**
     * 5. 下载文件
     *
     * @param downMsgList 下载文件信息
     */
    public void downFile(List<DownMsg> downMsgList) {
        for (DownMsg downMsg : downMsgList) {
            try {
                iFileByte.downFlv(downMsg);
                //iHttp.downFile(downMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 6. 保存json信息
     *
     * @param bVideoVO 视频信息
     * @param filePath 下载信息
     */
    public void saveJson(BVideoVO bVideoVO, String filePath) {
        DownMsg downMsg = new DownMsg();
        downMsg.setContent(bVideoVO.toString());
        downMsg.setFilePath(filePath);
        // 后缀名可以截取得到
        downMsg.setFileName(ConstantDir.video + ConstantCommon.JSON);

        IFileChar.saveStrToFile(downMsg);
    }

}
