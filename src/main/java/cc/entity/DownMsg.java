package cc.entity;

import cc.constant.Constant;
import cc.utils.FileUtils;
import lombok.Data;

import java.io.File;
import java.util.Map;

/**
 * @author 郭凯龙
 * @data 2021/9/3 0003
 */
@Data
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

//    public String getFilePath() {
//        return filePath;
//    }

    // 文件目录比如 [归属人，aid，某些] 分为多级目录，每层级分别校验
    public void setFilePath(String... dir) {
        String filePath = Constant.fileRootPath;
        for(String s:dir){
            filePath += FileUtils.checkFileNameAndPath(s) + File.separator;
        }
        this.filePath = filePath;
    }

//    public String getFileName() {
//        return fileName;
//    }

    public void setFileName(String fileName) {
        fileName = FileUtils.checkFileNameAndPath(fileName);
        this.fileName = fileName;
    }
}
