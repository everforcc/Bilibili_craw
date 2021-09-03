package cc.enums;

import cc.exception.ECode;

/**
 * @author guokailong 2021-09-01
 */
public enum CodeEnum implements ECode{

    //public static String Manage = "1";

    JSON_NULL("json","json返回数据为空"),
    JSON_Wrong("json","json传递参数有误"),
    AID_NULL("aid","aid不能为空"),
    AID_Wrong("aid","aid返回数据为空"),
    BVID_NULL("bvid","bvid不能为空"),
    BVID_Wrong("bvid","bvid返回数据为空"),
    CIDID_NULL("cid","cid不能为空"),
    CIDID_Wrong("cid","cid返回数据为空")

    ;


    private String code;
    private String msg;

    CodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValue(String code) {
        CodeEnum[] danceEnums = values();
        for (CodeEnum danceEnum : danceEnums) {
            if (danceEnum.code().equals(code)) {
                return danceEnum.msg();
            }
        }
        return "3";
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }

    public static String Manage(){
        CodeEnum[] danceEnums = values();
        return danceEnums[0].msg;
    }

}
