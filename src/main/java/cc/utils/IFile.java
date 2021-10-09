package cc.utils;

import java.io.File;
import java.io.InputStream;

/**
 * @author everforcc 2021-09-06
 */
public interface IFile {

    default void downFile(InputStream inputStream, File file){

    };

    default void saveStrToFile(String str, File file){

    };

    default String readStrToFile(String str, File file){
        return "";
    };

}
