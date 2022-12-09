/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 09:44
 * Copyright
 */

package cc.utils.busi;

import cc.enums.CodeExceptionEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class CheckReturn {

    /**
     * {"code":-404,"message":"啥都木有","ttl":1}
     *
     * @param jsonObject json对象
     */
    public static void checkJson(JSONObject jsonObject) {
        String code = jsonObject.getString("code");
        CodeExceptionEnum.JSON_NULL.error(!"0".equals(code), jsonObject.getString("message"));
    }

    public static void checkJson(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        String code = jsonObject.getString("code");
        CodeExceptionEnum.JSON_NULL.error(!"0".equals(code), "-" + jsonObject.getString("message"));
    }

}
