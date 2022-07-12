/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 10:52
 * Copyright
 */

package cc.utils;

import cc.utils.file.FileNameUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Yukino
 * 2020/3/3
 */
@Slf4j
public class Method_down {

    /**
     * 下载自定义文件名称  图片
     *
     * @param url      文件路径,不需要请求头的
     * @param fileName 文件名
     * @throws Exception 抛出异常
     */
    public static void downByUrl(String url, String dir, String fileName) throws Exception {
        URL uri = new URL(url);
        InputStream in = uri.openStream();
        commonDownFile(uri.openStream(), dir + "\\", fileName, new BigDecimal(in.available()));
    }

    /**
     * 下载视频，需要请求头
     *
     * @param flvUrl        视频地址
     * @param id            视频id
     * @param dir           文件路径
     * @param fileName      文件名
     * @param requestMethod 请求方式
     * @throws Exception 抛出异常
     */
    public static void downFlv(String flvUrl, String id, String dir, String fileName, String requestMethod) throws Exception {
        HttpURLConnection conn = Request_Heard.requestHeard_downFlv(flvUrl, id, requestMethod);
        BigDecimal fileLength = new BigDecimal(conn.getContentLength());
        if (fileLength.compareTo(new BigDecimal(1)) < 1) {
            fileLength = new BigDecimal(conn.getHeaderField("Content-Length"));
        }
        log.info("视频大小:" + calSize(fileLength));
        // 文件如何拆分的问题，估计最后还是的计算，但是结果可以就好
        // 增加1023 加载自身一位正好1 k
        // 可以分批下载到本地再合并，多个线程
//        for(int i=0;i<38287700;i++) {
//            conn = Request_Heard.requestHeard_downFlvBySplit(flvUrl,"av"+aid,requestMethod,"bytes=" + i +"-"+(i+=1023) );
//            file(conn.getInputStream(), Constant.rootFilePath + dir + "\\", i + fileName);
//        }
        commonDownFile(conn.getInputStream(), dir + "\\", fileName, fileLength);
    }

    // 改名字的时候用
//    public static void rename(String oldName,String newName){
//        File file = new File(Constant.rootFilePath+oldName);
//        file.renameTo(new File(Constant.rootFilePath+newName));
//        log.info(Constant.rootFilePath+oldName + "改名为:" + Constant.rootFilePath+newName);
//    }

    /**
     * 用apache的copy下载 小文件
     * 大文件输出个 1%,随时知道情况
     *
     * @param inputStream 输入流
     * @param filePath    文件路径
     * @param fileName    文件名
     * @throws Exception 抛出异常
     */
    private static void commonDownFile1(InputStream inputStream, String filePath, String fileName) throws Exception {
        log.info("开始");
        FileUtils.copyToFile(inputStream, new File(filePath + File.separator + fileName));
        log.info("结束");
    }

    /**
     * 公共的下载文件的方法
     *
     * @param inputstream 输入流
     * @param filePath    文件路径
     * @param fileName    文件名
     * @throws Exception 抛出异常
     */
    private static void commonDownFile(InputStream inputstream, String filePath, String fileName, BigDecimal fileLength) throws Exception {
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
        File file = new File(filePath + fileName);
        if (file.exists()) {
            log.info("文件已经存在:" + filePath + fileName);
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
                tempRate = new BigDecimal(df.format(tempLength.multiply(new BigDecimal(100)).divide(fileLength, 1, RoundingMode.DOWN)));
                if (tempRate.compareTo(rate) > 0) {
                    log.info(fileName + ":" + tempRate + "%");
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
            log.info("文件大小 【{}】", result);
            return result;
        } else if (bigDecimal.compareTo(base = base.multiply(twoPower10)) <= 0) {
            // bigDecimal单位是 byte, 这里*1024/1024/1024 结果是 kb
            String result = bigDecimal.multiply(twoPower10).divide(base, 2, RoundingMode.HALF_UP) + "kb";
            log.info("文件大小 【{}】", result);
            return result;
        } else if (bigDecimal.compareTo(base = base.multiply(twoPower10)) <= 0) {
            String result = bigDecimal.multiply(twoPower10).divide(base, 2, RoundingMode.HALF_UP) + "MB";
            log.info("文件大小 【{}】", result);
            return result;
        } else if (bigDecimal.compareTo(base = base.multiply(twoPower10)) <= 0) {
            String result = bigDecimal.multiply(twoPower10).divide(base, 2, RoundingMode.HALF_UP) + "GB";
            log.info("文件大小 【{}】", result);
            return result;
        }

        return "-1b 文件过大";
    }

    /**
     * 记录日志用 dir 和filename 重复了
     *
     * @param dir      记录文本日志
     * @param fileName 文件名
     * @param content  文件内容
     */
    public static void recorMsgd(String dir, String fileName, String content) {
        try {
            File file = new File(dir);
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                log.info("创建结果 【{}】", mkdirs);
            }
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write("\r\n" + content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String path = "E:/filesystem/project/Bilibili_craw/file/";
        String fileName = "test.txt";
        File file = new File(path);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            log.info("创建结果 【{}】", mkdirs);
        }
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(path + File.separator + fileName, true);
            fileWriter.append("123");
            fileWriter.close();

            File result = new File(path + File.separator + fileName);
            System.out.println(result.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}