/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-15 12:03
 * Copyright
 */

package cc.utils.file;

import cc.constant.ConstantCharset;
import cc.utils.busi.DownMsg;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * 保存字符信息
 * 默认使用apache
 */
public interface IFileChar {

    /**
     * 1.1 保存到文件
     *
     * @param file    保存字符串到文件
     * @param content 文件内容
     */
    @SneakyThrows
    static void saveStrToFile(File file, String content) {
        FileUtils.writeStringToFile(file, content, ConstantCharset.UTF_8);
    }

    /**
     * 1.2 保存到文件
     *
     * @param dir      记录文本日志
     * @param fileName 文件名
     * @param content  文件内容
     */
    @SneakyThrows
    static void saveStrToFile(String dir, String fileName, String content) {
        // 文件对象
        File file = new File(dir + File.separator + fileName);
        FileUtils.writeStringToFile(file, content, ConstantCharset.UTF_8);
    }

    /**
     * 1.3 保存到文件
     *
     * @param downMsg 文件信息
     */
    @SneakyThrows
    static void saveStrToFile(DownMsg downMsg) {
        // 文件对象
        File file = new File(downMsg.getFilePath() + File.separator + downMsg.getFileName());
        FileUtils.writeStringToFile(file, downMsg.getContent(), ConstantCharset.UTF_8);
    }

    /**
     * 2.1 从从文件内读取数据
     *
     * @param file 文件对象
     * @return 读取内容
     */
    @SneakyThrows
    static String readFileToString(File file) {
        return FileUtils.readFileToString(file, ConstantCharset.UTF_8);
    }

    /**
     * 2.2  从文件内读取数据
     *
     * @param filePath 文件路径
     * @return 读取内容
     */
    @SneakyThrows
    static String readFileToString(String filePath, String fileName) {
        File file = new File(filePath + File.separator + fileName);
        return FileUtils.readFileToString(file, ConstantCharset.UTF_8);
    }

    /**
     * 2.3  从文件内读取数据
     *
     * @param downMsg 文件信息
     * @return 读取内容
     */
    @SneakyThrows
    static String readFileToString(DownMsg downMsg) {
        File file = new File(downMsg.getFilePath() + File.separator + downMsg.getFileName());
        return FileUtils.readFileToString(file, ConstantCharset.UTF_8);
    }

    /**
     * 3.1 校验文件是否存在
     *
     * @param downMsg 文件信息
     * @return 是否存在
     */
    @SneakyThrows
    static boolean fileExist(DownMsg downMsg) {
        File file = new File(downMsg.getFilePath() + File.separator + downMsg.getFileName());
        return file.exists();
    }

}
