package cc.entity;

import java.util.Map;

/**
 * @author 郭凯龙
 * @data 2021/9/3 0003
 */
public class DownMsg {

    // 下载文件需要的信息
    private String url;
    private String type;
    private Map<String,String> header;
    private String filePath;
    private String fileName;

    /**
     * 备用信息
     */
    private String otherMsg;

}
