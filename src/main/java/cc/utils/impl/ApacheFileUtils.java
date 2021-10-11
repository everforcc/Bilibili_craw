package cc.utils.impl;

import cc.constant.Constant;
import cc.utils.IFile;
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
            FileUtils.writeStringToFile(file,str, Constant.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    public String readStrToFile(String str, File file){
        try {
            return FileUtils.readFileToString(file,Constant.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    };

}
