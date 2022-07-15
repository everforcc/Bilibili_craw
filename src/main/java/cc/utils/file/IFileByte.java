package cc.utils.file;

import cc.entity.DownMsg;

import java.io.InputStream;
import java.math.BigDecimal;

/**
 * 默认使用apache
 *
 * @author everforcc 2021-09-06
 */
public interface IFileByte {

    /**
     * 1. 下载文件
     * 用apache的copy下载 小文件
     * 大文件输出个 1%,随时知道情况
     *
     * @param inputStream 输入流
     * @param filePath    文件路径
     * @param fileName    文件名
     */
    void downFile(InputStream inputStream, String filePath, String fileName);

    /**
     * 2. 下载文件带进度条
     *
     * @param inputStream 输入流
     * @param filePath    文件路径
     * @param fileName    文件名
     * @param fileLength  文件大小
     */
    void downFile(InputStream inputStream, String filePath, String fileName, BigDecimal fileLength);

    /**
     * 3. 根据url下载文件
     *
     * @param downMsg  下载对象信息
     */
    void downFlv(DownMsg downMsg);

    /**
     * 4. 根据url下载小文件
     *
     * @param url      文件url
     * @param dir      文件路径
     * @param fileName 文件名
     */
    void downByUrl(String url, String dir, String fileName);

}
