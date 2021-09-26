package cc.entity;

import lombok.Data;

import java.util.Map;

/**
 * @author everforcc 2021-09-07
 */
@Data
public class DownMsgRefa {

    // 下载文件需要的信息
    private String type;
    private Map<String,String> header;//也可设置子类只有url，path不同，不用存太多headers信息，父类存一个即可
    private String charset;

    @Data
    public class own{
        private String url;
        private String filePath;
        private String fileName;
        /**
         * 备用信息
         */
        private String otherMsg;
    }

}
