package cc.bilibili.impl;

import cc.bilibili.IVideo;
import cc.constant.ConstantHeader;
import cc.constant.ConstantVideoFlvURL;
import cc.entity.DownMsg;
import cc.enums.CodeEnum;
import cc.utils.IHttp;
import cc.utils.impl.HttpUrlConnectionUtils;
import cc.vo.BVideoVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 郭凯龙
 * @data 2021/9/3 0003
 */
@Slf4j
public class VideoFlv implements IVideo {

    private static final IHttp iHttp = new HttpUrlConnectionUtils();

    // 下载短视频，flv格式，

    /* 每个方法的开头都要保证数据的有效性 */

    /**
     * 0.前置流程 拿到录入（链接，av，bv，其他情况），处理为av号
     * 1.av拿到cid 集合，因为一个av会有多个视频
     * 2.cid处理文件名，目录
     * 3.下载
     */

    public static void flow(String aid){
        // 1. 根据aid获取cid json
        String cidJson = getCidJsonByAid(aid);
        // 2. cid转换VO
        BVideoVO bVideoVO = getCidVO(cidJson);
        // 3. 文件信息
        // 3.1 获取文件地址
        // 3.2 组织问价信息
        List<DownMsg> downMsgList = getFileMsg(bVideoVO);
        // 4. 下载文件,当前为单个下载，所有信息都有了，可以调整
        down(downMsgList);
    }

    /**
     * 1. 根据aid获取cid json
     * @param aid
     * @return
     */
    private static String getCidJsonByAid(String aid){
        // 不能为空
        CodeEnum.AID_NULL.isEffect("".equals(aid));
        // 根据id请求必须有返回数据
        // 根据aid拿到cid的数据
        String urlPath = String.format(ConstantVideoFlvURL.aidToCid,aid);
        return iHttp.get(urlPath,ConstantVideoFlvURL.aidToCidType, ConstantHeader.map,ConstantVideoFlvURL.charset);
    }

    /**
     * 2. cid转换VO
     * @param json
     * @return
     */
    private static BVideoVO getCidVO(String json){
        // 校验
        JSONObject jsonObject = checkJson(json);
        // 校验过后取出需要的数据
        String bVideoString = jsonObject.getString("data");
        // 转换VO
        BVideoVO bVideoVO = JSONObject.parseObject(bVideoString,BVideoVO.class);
        //log.info(bVideoVO.toString());
        return bVideoVO;
    }

    /**
     * 因为每个cid对应一个视频所以要在方法内组装好信息
     * 3. 文件信息
     * 3.1 获取文件地址
     * 3.2 组织问价信息
     * @param bVideoVO
     * @return
     */
    private static List<DownMsg> getFileMsg(BVideoVO bVideoVO){
        List<DownMsg> downMsgList = new ArrayList<>();
        String aid = bVideoVO.getAid();
        List<BVideoVO.CidVO> cidVOList =  bVideoVO.getPages();
        for(BVideoVO.CidVO cidVO:cidVOList) {
            DownMsg downMsg = new DownMsg();
            // 校验
            // 组装链接
            String urlPath = String.format(ConstantVideoFlvURL.aidCidToRealVideoUrl, aid, cidVO.getCid(), "120");
            // 请求
            String videoJson = iHttp.get(urlPath, ConstantVideoFlvURL.aidCidToRealVideoUrlType, ConstantHeader.map,ConstantVideoFlvURL.charset);
//            System.out.println("cidVO.getCid():" + cidVO.getCid());
//            System.out.println("videoJson:" + videoJson);
            String realUrl = getRealFlvUrl(videoJson);

            // 组织文件信息
            downMsg.setUrl(realUrl);
            downMsg.setFilePath("短视频",bVideoVO.getOwner().getName());
            // TODO 可以手动格式化个格式 [AV][PART].flv
            downMsg.setFileName(bVideoVO.getAid() + cidVO.getPart() + ConstantVideoFlvURL.downFileType);
            downMsg.setType(ConstantVideoFlvURL.downFileUrlType);
            downMsg.setHeader(ConstantHeader.mapFlv);
            downMsgList.add(downMsg);
        }
        return downMsgList;
    }

    /**
     * 视频真实url
     * @param json
     * @return
     */
    private static String getRealFlvUrl(String json){
        JSONObject jsonObject = checkJson(json);
        // url在json中的位置
        JSONObject realUrlObj = (JSONObject)jsonObject.getJSONObject("data").getJSONArray("durl").get(0);
        String realUrl = realUrlObj.getString("url");
        return realUrl;
    }


    /**
     * 校验所有的json
     *
     * @param json
     * @return
     */
    private static JSONObject checkJson(String json){
        CodeEnum.JSON_NULL.isEffect("".equals(json));
        JSONObject jsonObject = JSON.parseObject(json);
        String code = jsonObject.getString("code");
        CodeEnum.JSON_Wrong.isEffect(!"0".equals(code),jsonObject.getString("message"));
        return jsonObject;
    }

    private static void down(List<DownMsg> downMsgList){
        // 地址，文件路径，文件名。type，headers
        /*for(DownMsg downMsg:downMsgList) {
            iHttp.downFile(downMsg);
        }*/
        downMsgList.forEach(iHttp::downFile);
    }

    public static void main(String[] args) {
        //flow(null); 170001
        flow("170001");
    }



}
