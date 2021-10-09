package cc.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author everforcc 2021-10-09
 */
public class JSONString {

    @Override
    public String toString() {

        return JSON.toJSON(this).toString();
    }
}
