package c.c.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Yukino
 * 2020/3/6
 * 待完善
 */
public class JsonHelp {


    //obj >> obj
    //
    //obj >> arr
    //
    //arr >> obj
    //
    //arr >> arr

    //如果是数组还要判断

    private  JSONObject jsonObject;

    private  JSONArray jsonArray;

    private List<JSONArray> jsonArrayList;


    public JsonHelp(String json) {
        this.jsonObject = JSONObject.fromObject(json);
    }

    public JsonHelp(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JsonHelp(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    /**
     * 返回json Obj
     * @param key
     * @return
     */
    public  JsonHelp obj(String key){
        JsonHelp jsonHelp = null;
        if(jsonObject!=null) {
            jsonHelp = new JsonHelp(jsonObject.getJSONObject(key));
            return jsonHelp;
        }else if(jsonArray!=null){
            List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
            for(int i=0;i<jsonArray.size();i++){
                JSONObject arrJsonObj = (JSONObject)jsonArray.get(i);
                jsonObjectList.add(arrJsonObj);
            }
            jsonHelp = new JsonHelp(jsonObject.getJSONObject(key));
        }

        return jsonHelp;
    }

    /**
     * 返回json Ary
     * @param key
     * @return
     */
    public  JsonHelp ary(String key){
        JsonHelp jsonHelp = new JsonHelp(jsonObject.getJSONArray(key));
        return jsonHelp;
    }

    /**
     * 返回str
     * @param key
     * @return
     */
    public  String str(String key){
        String str = jsonObject.getString(key);
        return str;
    }


}
