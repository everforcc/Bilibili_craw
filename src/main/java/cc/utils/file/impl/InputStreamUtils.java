package cc.utils.file.impl;

import cc.busi.config.Bilibili_Heard;
import cc.entity.DownMsg;
import cc.utils.file.FileNameUtils;
import cc.utils.file.IFileByte;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 主要处理下载大文件有进度条
 *
 * @author everforcc 2021-09-06
 */
@Slf4j
public class InputStreamUtils implements IFileByte {

    /**
     * @param inputStream 输入流
     * @param filePath    文件路径
     * @param fileName    文件名
     */
    @Override
    public void downFile(InputStream inputStream, String filePath, String fileName) {

    }

    /**
     * 2. 下载文件带进度条,大文件
     * 大文件输出个 1%,随时知道情况
     *
     * @param inputStream 输入流
     * @param filePath    文件路径
     * @param fileName    文件名
     * @param fileLength  文件大小
     */
    public void downFile(InputStream inputStream, String filePath, String fileName, BigDecimal fileLength) {
        commonDownFile(inputStream, filePath, fileName, fileLength);
    }


    /**
     * 3. 根据url下载文件
     * <p>
     * flvUrl   文件url
     * dir      文件路径
     * fileName 文件名
     *
     * @param downMsg 下载对象信息
     */
    @SneakyThrows
    public void downFlv(DownMsg downMsg) {

        // 取出文件信息
        String flvUrl = downMsg.getUrl();
        String filePath = downMsg.getFilePath();
        String fileName = downMsg.getFileName();

        // 取出请求头
        HttpURLConnection conn = Bilibili_Heard.requestHeard_downFlv(flvUrl, downMsg);
        BigDecimal fileLength = new BigDecimal(conn.getContentLength());
        if (fileLength.compareTo(new BigDecimal(1)) < 1) {
            fileLength = new BigDecimal(conn.getHeaderField("Content-Length"));
        }
        // 文件如何拆分的问题，估计最后还是的计算，但是结果可以就好
        // 增加1023 加载自身一位正好1 k
        // 可以分批下载到本地再合并，多个线程
//        for(int i=0;i<38287700;i++) {
//            conn = Request_Heard.requestHeard_downFlvBySplit(flvUrl,"av"+aid,requestMethod,"bytes=" + i +"-"+(i+=1023) );
//            file(conn.getInputStream(), Constant.rootFilePath + dir + "\\", i + fileName);
//        }
        commonDownFile(conn.getInputStream(), filePath, fileName, fileLength);
    }

    /**
     * 4. 根据url下载小文件
     *
     * @param url      文件url
     * @param dir      文件路径
     * @param fileName 文件名
     */
    @SneakyThrows
    public void downByUrl(String url, String dir, String fileName) {
        URL uri = new URL(url);
        InputStream in = uri.openStream();
        // 小文件可能不准,获取不到准确的文件大小
        commonDownFile(uri.openStream(), dir, fileName, new BigDecimal(in.available()));
    }

    /**
     * 公共的下载文件的方法
     *
     * @param inputstream 输入流
     * @param filePath    文件路径
     * @param fileName    文件名
     */
    @SneakyThrows
    private static void commonDownFile(InputStream inputstream, String filePath, String fileName, BigDecimal fileLength) {
        log.info("文件大小:" + calSize(fileLength));
        // 总时间
        Date begindate = new Date();

        // windows的命名规则校验, 文件夹暂时不需要
        fileName = FileNameUtils.checkFileNameAndPath(fileName);
        // 创建文件夹和文件名
        File saveFile = new File(filePath);
        if (!saveFile.exists()) {
            boolean mkdirs = saveFile.mkdirs();
            log.info("创建结果 【{}】", mkdirs);
        }
        File file = new File(filePath + File.separator + fileName);
        if (file.exists()) {
            log.info("文件已经存在:" + file.getAbsolutePath());
            return;
        }
        log.info("文件不存在 【{}】 开始下载", file.getAbsolutePath());
        FileOutputStream fo = new FileOutputStream(file);

        // 格式化结果
        DecimalFormat df = new DecimalFormat("00");

        // 以流的方式进行下载
        byte[] buf = new byte[1024];
        int length = 0;
        BigDecimal tempLength = new BigDecimal(length);
        BigDecimal rate = new BigDecimal("0.01");
        BigDecimal tempRate;
        while ((length = inputstream.read(buf, 0, buf.length)) != -1) {
            if (fileLength.compareTo(new BigDecimal(0)) > 0) {
                tempLength = tempLength.add(new BigDecimal(length));
                // 每 1% 跳出一行数据
                /**
                 * 1. tempLength 是每次读取的字节数
                 * 2. tempLength*100
                 * 3. 结果除以 文件总长 截断保留一位小数
                 * 4.
                 */
                log.info("tempLength: 【{}】", tempLength);
                log.info("fileLength: 【{}】", fileLength);
                tempRate = new BigDecimal(df.format(tempLength.multiply(new BigDecimal(100)).divide(fileLength, 1, RoundingMode.DOWN)));
                if (tempRate.compareTo(rate) > 0) {
                    log.info("文件下载进度【{}】【{}%】", fileName, tempRate);
                    rate = tempRate;
                }
            }
            fo.write(buf, 0, length);
        }
        inputstream.close();
        fo.close();
        log.info(filePath + fileName + "下载完成");

        // 计算下载所用时间
        Date enddate = new Date();
        double time = enddate.getTime() - begindate.getTime();
        // log.info(fileLength.divide(new BigDecimal(1024),2, BigDecimal.ROUND_HALF_UP)+"");

        // 算是每秒多少 b
        BigDecimal bigDecimal = fileLength.divide(new BigDecimal(time / 1000), 2, RoundingMode.HALF_UP);
        log.info("耗时：" + time / 1000 + "s" + ",速度:" + calSize(bigDecimal) + "/s");
        // 可以转码但是需要本地ffmpeg的支持，没有的话就注释掉
        // ChangeVedioCover.createFileCover(file);
    }

    /**
     * 速度超过1024优化为b,kb,M，G等  1024~n 次方  有个方法找出接近2的n次方 hashmap     可以用递归取结果的方式 =0 i 累加
     * 这里将 byte处理为合适的单位,
     * 每隔 1024 进化一个单位
     * multiply 乘
     * divide 除
     *
     * @param bigDecimal byte大小
     * @return 合适单位的字符串
     */
    private static String calSize(BigDecimal bigDecimal) {
        BigDecimal base = new BigDecimal(1);
        BigDecimal twoPower10 = new BigDecimal(1 << 10);
        // 判断是否小于 1024b
        if (bigDecimal.compareTo(base = base.multiply(twoPower10)) <= 0) {
            // bigDecimal单位是 byte, 这里*1024/1024 结果还是 byte
            String result = bigDecimal.multiply(twoPower10).divide(base, 2, RoundingMode.HALF_UP) + "b";
            //log.info("文件大小 【{}】", result);
            return result;
        } else if (bigDecimal.compareTo(base = base.multiply(twoPower10)) <= 0) {
            // bigDecimal单位是 byte, 这里*1024/1024/1024 结果是 kb
            String result = bigDecimal.multiply(twoPower10).divide(base, 2, RoundingMode.HALF_UP) + "kb";
            //log.info("文件大小 【{}】", result);
            return result;
        } else if (bigDecimal.compareTo(base = base.multiply(twoPower10)) <= 0) {
            String result = bigDecimal.multiply(twoPower10).divide(base, 2, RoundingMode.HALF_UP) + "MB";
            //log.info("文件大小 【{}】", result);
            return result;
        } else if (bigDecimal.compareTo(base = base.multiply(twoPower10)) <= 0) {
            String result = bigDecimal.multiply(twoPower10).divide(base, 2, RoundingMode.HALF_UP) + "GB";
            //log.info("文件大小 【{}】", result);
            return result;
        }
        return "-1b 文件过大";
    }

    public static void main(String[] args) {

        // 文件大小
        BigDecimal fileLength = new BigDecimal(70.86 * 1024);
        int size = 70 * 1024;
        // 格式化结果
        DecimalFormat df = new DecimalFormat("00");

        int length = 0;
        BigDecimal tempLength = new BigDecimal(length);
        BigDecimal rate = new BigDecimal("0.01");
        BigDecimal tempRate;
        byte[] buf = new byte[1024];
        while ((length = (size = size - 1024)) > 0) {
            if (fileLength.compareTo(new BigDecimal(0)) > 0) {
                tempLength = tempLength.add(new BigDecimal(length));
                // 每 1% 跳出一行数据
                tempRate = new BigDecimal(df.format(tempLength.multiply(new BigDecimal(100)).divide(fileLength, 1, RoundingMode.DOWN)));
                if (tempRate.compareTo(rate) > 0) {
                    log.info("文件下载进度【{}%】", tempRate);
                    rate = tempRate;
                }
            }
            //  fo.write(buf, 0, length);
        }
    }

}
