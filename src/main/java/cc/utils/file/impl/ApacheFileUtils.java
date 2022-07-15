package cc.utils.file.impl;

import cc.entity.DownMsg;
import cc.utils.file.IFileByte;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 * @author everforcc 2021-09-06
 */
@Slf4j
public class ApacheFileUtils implements IFileByte {

    /**
     * 用apache的copy下载 小文件
     * 大文件输出个 1%,随时知道情况
     *
     * @param inputStream 输入流
     * @param filePath    文件路径
     * @param fileName    文件名
     */
    public void downFile(InputStream inputStream, String filePath, String fileName) {
        File file = new File(filePath + fileName);
        try {
            log.info("下载文件: 【{}】", file.getAbsolutePath());
            FileUtils.copyToFile(inputStream, file);
            log.info("下载文件: 【{}】 结束", file.getAbsolutePath());
        } catch (IOException e) {
            log.error("下载文件: 【{}】", file.getAbsolutePath(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void downFile(InputStream inputStream, String filePath, String fileName, BigDecimal fileLength) {

    }

    @Override
    public void downFlv(DownMsg downMsg) {

    }

    @Override
    public void downByUrl(String url, String dir, String fileName) {

    }

}
