package cc.utils.busi;

import cc.constant.ConstantDir;
import cc.utils.file.FileNameUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.*;

import java.io.File;
import java.util.Map;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DownMsg {

    // 下载文件需要的信息
    private String url;
    private String aid;
    private String reqType;
    private Map<String,String> header;//也可设置子类只有url，path不同，不用存太多headers信息，父类存一个即可
    private String filePath;
    private String fileName;
    private String filetype;
    private String charset;
    private String content;

    /**
     * 备用信息
     */
    private String otherMsg;

//    public String getFilePath() {
//        return filePath;
//    }

    /**
     * upid/{av,album}/号/内容{图片，视频}，json
     * ep
     * ss
     */
    // 文件目录比如 [归属人，aid，某些] 分为多级目录，每层级分别校验
    public void setFilePath(String... dir) {
        StringBuilder filePath = new StringBuilder(ConstantDir.fileRootPath);
        for(String s:dir){
            filePath.append(FileNameUtils.checkFileNameAndPath(s)).append(File.separator);
        }
        this.filePath = filePath.toString();
    }

    /**
     * 直接指定全路径
     * @param filePath 全路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

//    public String getFileName() {
//        return fileName;
//    }

    public void setFileName(String fileName) {
        fileName = FileNameUtils.checkFileNameAndPath(fileName);
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
