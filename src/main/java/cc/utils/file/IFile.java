package cc.utils.file;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 * @author everforcc 2021-09-06
 */
public interface IFile {

    default void downFile(InputStream inputStream, String filePath, String fileName){

    };

    default void downFile(InputStream inputStream, String filePath, String fileName, BigDecimal fileLength){

    }

    default void saveStrToFile(String str, File file){

    };

    default String readStrToFile(String str, File file){
        return "";
    };

}
