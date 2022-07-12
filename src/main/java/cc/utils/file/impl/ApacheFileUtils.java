package cc.utils.file.impl;

import cc.constant.ConstantCommon;
import cc.utils.file.IFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author everforcc 2021-09-06
 */
public class ApacheFileUtils implements IFile {

    public void downFile(InputStream inputStream, String filePath, String fileName){
        try {
            FileUtils.copyToFile(inputStream,new File(filePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    public void saveStrToFile(String str, File file){
        try {
            FileUtils.writeStringToFile(file,str, ConstantCommon.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    public String readStrToFile(String str, File file){
        try {
            return FileUtils.readFileToString(file, ConstantCommon.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    };

}
