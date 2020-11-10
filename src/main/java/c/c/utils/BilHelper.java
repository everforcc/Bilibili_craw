package c.c.utils;

/**
 * Yukino
 * 2020/6/22
 */
public class BilHelper {


    public String inputToAV(String aid){
        if("".equals(aid)||null==aid){
            return "false";
        }
        //aid=aid.toUpperCase();
        if(aid.startsWith("AV")||aid.startsWith("av")){
            return  aid.substring(2,aid.length());
        }else if (aid.startsWith("BV")){ // BV格式的判断       'BV1  4 1 7  '    正则表达式来判断
            // 解决方案1.计算出来
            // 解决方案2 http://api.bilibili.com/x/web-interface/archive/stat?bvid=BV17x411w7KC 还是使用接口去查询
            // System.out.println(String.valueOf(Bilibili_base58.dec(aid)));
            return String.valueOf(Bilibili_base58.dec(aid));
        }else if((aid.matches("\\d*"))){//判断是否为纯数字
            return aid;
        }
        return "false";
    }

}
