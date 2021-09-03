package cc.bilibili.impl;

import cc.bilibili.Video;
import cc.constant.ConstantHeader;
import cc.constant.ConstantVideoFlvURL;
import cc.enums.CodeEnum;
import cc.utils.IHttp;
import cc.utils.impl.HttpUrlConnectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

/**
 * @author 郭凯龙
 * @data 2021/9/3 0003
 */
public class VideoFlv implements Video{

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
        checkAid(aid);
    }

    private static void getCidListByAid(){

    }

    private static void checkAid(String aid){
        // 不能为空
        CodeEnum.AID_NULL.isEffect("".equals(aid));
        // 根据id请求必须有返回数据
        // 根据aid拿到cid的数据

        String urlPath = String.format(ConstantVideoFlvURL.aidToCid,aid);
        String json = iHttp.get(urlPath,ConstantVideoFlvURL.aidToCidType, ConstantHeader.map);
        System.out.println(json);
        checkJson(json);
    }

    public static void checkJson(String json){
        CodeEnum.JSON_NULL.isEffect("".equals(json));
        JSONObject jsonObject = JSON.parseObject(json);
        String code = jsonObject.getString("code");
        CodeEnum.JSON_Wrong.isEffect(!"0".equals(code),jsonObject.getString("message"));
        // 校验过后取出需要的数据

    }

    public static void main(String[] args) {
        //flow(null); 170001
        flow("170001");
    }



}
