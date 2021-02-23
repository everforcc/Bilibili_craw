package c.c.utils;

import c.c.utils.Constant;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Yukino
 * 2020/3/3
 */
public class Method_down {

    // 记录日志
    private static Print_Record println = Print_Record.getInstanse("");

    /**
     * 下载自定义文件名称  图片
     * @param url
     * @param fileName
     * @throws Exception
     */
    public static void downByUrl(String url,String dir,String fileName) throws Exception{
        URL uri = new URL(url);
        InputStream in = uri.openStream();
        commonDownFile(uri.openStream(),Constant.rootFilePath+dir+"\\",fileName,new BigDecimal(in.available()));
    }

    /**
     * 下载视频，需要请求头
     * @param flvUrl
     * @param id
     * @param dir
     * @param fileName
     * @param requestMethod
     * @throws Exception
     */
    public static void downFlv(String flvUrl,String id,String dir,String fileName,String requestMethod)throws Exception{
        HttpURLConnection conn = Request_Heard.requestHeard_downFlv(flvUrl,id,requestMethod);
        BigDecimal fileLength = new BigDecimal(conn.getContentLength());
        if(fileLength.compareTo(new BigDecimal(1))<1) {
            fileLength = new BigDecimal(conn.getHeaderField("Content-Length"));
        }
        println.println("视频大小:" + calSize(fileLength));
        // 文件如何拆分的问题，估计最后还是的计算，但是结果可以就好
        // 增加1023 加载自身一位正好1 k
        // 可以分批下载到本地再合并，多个线程
        /*for(int i=0;i<38287700;i++) {
            conn = Request_Heard.requestHeard_downFlvBySplit(flvUrl,"av"+aid,requestMethod,"bytes=" + i +"-"+(i+=1023) );
            file(conn.getInputStream(), Constant.rootFilePath + dir + "\\", i + fileName);
        }*/
        commonDownFile(conn.getInputStream(), Constant.rootFilePath + dir + "\\",  fileName,fileLength);
    }

    // 改名字的时候用
    /*public static void rename(String oldName,String newName){
        File file = new File(Constant.rootFilePath+oldName);
        file.renameTo(new File(Constant.rootFilePath+newName));
        println.println(Constant.rootFilePath+oldName + "改名为:" + Constant.rootFilePath+newName);
    }*/

    private static void commonDownFile1(InputStream in,String filePath,String fileName,BigDecimal fileLength)throws Exception{
        println.println( "开始" );
        FileUtils.copyToFile(in,new File("D://2.flv"));
        println.println( "结束");
    }

    /**
     *  公共的下载文件的方法
     * @param in
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    private static void commonDownFile(InputStream in,String filePath,String fileName,BigDecimal fileLength)throws Exception{
        // 总时间
        Date begindate = new Date();

        // windows的命名规则校验, 文件夹暂时不需要
        fileName = Common_Method.checkFileName(fileName);
        /**
         * 创建文件夹和文件名
         */
        File saveFile = new File(filePath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        File file = new File(filePath+fileName);
        if(file.exists()){
            println.println( "文件已经存在:" + filePath + fileName );
           return;
        }
        println.println("开始下载");
        FileOutputStream fo = new FileOutputStream(file);
        DecimalFormat df = new DecimalFormat("00");
        // 比例
        /**
         * 以流的方式进行下载
         */
        byte[] buf = new byte[1024];
        int length = 0;
        BigDecimal tempLength = new BigDecimal(length);
        BigDecimal rate = new BigDecimal(0.01);
        BigDecimal tempRate = new BigDecimal(0);
        while ((length = in.read(buf, 0, buf.length)) != -1) {
            tempLength = tempLength.add(new BigDecimal(length));
            // 每 1% 跳出一行数据
            tempRate = new BigDecimal( df.format(tempLength.multiply(new BigDecimal(100)).divide(fileLength,1,BigDecimal.ROUND_DOWN)));
            if(tempRate.compareTo(rate)==1) {
                println.println( fileName + ":" + tempRate + "%" );
                rate = tempRate;
            }
            fo.write(buf, 0, length);
        }
        in.close();
        fo.close();
        println.println(filePath+fileName + "下载完成");
        /**
         * 计算下载所用时间
         */
        Date enddate = new Date();
        double time = enddate.getTime() - begindate.getTime();
        // println.println(fileLength.divide(new BigDecimal(1024),2, BigDecimal.ROUND_HALF_UP)+"");
        // 也可以进一步优化，比如速度超过1024优化为b,kb,M，G等  1024~n 次方  有个方法找出接近2的n次方 hashmap     可以用递归取结果的方式 =0 i 累加
        BigDecimal bigDecimal = fileLength.divide(new BigDecimal(time / 1000),2, BigDecimal.ROUND_HALF_UP);
        println.println("耗时：" + time / 1000 + "s" + ",速度:" + calSize(bigDecimal) + "/s" );
        // 可以转码但是需要本地ffmpeg的支持，没有的话就注释掉
        // ChangeVedioCover.createFileCover(file);
    }

    private static String calSize(BigDecimal bigDecimal){
        BigDecimal base = new BigDecimal(1);
        BigDecimal twoPower10 = new BigDecimal(1 << 10);
        if(bigDecimal.compareTo(base = base.multiply(twoPower10))<=0){
            System.out.println(bigDecimal.multiply(twoPower10).divide(base,2, BigDecimal.ROUND_HALF_UP));
            return bigDecimal.multiply(twoPower10).divide(base,2, BigDecimal.ROUND_HALF_UP) + "b";
        }else if(bigDecimal.compareTo(base = base.multiply(twoPower10))<=0){
            System.out.println(bigDecimal.multiply(twoPower10).divide(base,2, BigDecimal.ROUND_HALF_UP));
            return bigDecimal.multiply(twoPower10).divide(base,2, BigDecimal.ROUND_HALF_UP) + "kb";
        }else if(bigDecimal.compareTo(base = base.multiply(twoPower10))<=0){
            System.out.println(bigDecimal.multiply(twoPower10).divide(base,2, BigDecimal.ROUND_HALF_UP));
            return bigDecimal.multiply(twoPower10).divide(base,2, BigDecimal.ROUND_HALF_UP) + "MB";
        }else if(bigDecimal.compareTo(base = base.multiply(twoPower10))<=0){
            System.out.println(bigDecimal.multiply(twoPower10).divide(base,2, BigDecimal.ROUND_HALF_UP));
            return bigDecimal.multiply(twoPower10).divide(base,2, BigDecimal.ROUND_HALF_UP) + "GB";
        }/*else if(bigDecimal.compareTo(base = base.multiply(new BigDecimal(1024)))<=0){
            System.out.println(bigDecimal.divide(base,2, BigDecimal.ROUND_HALF_UP));
            return bigDecimal.divide(base,2, BigDecimal.ROUND_HALF_UP) + "GGb";
        }*/
        return "213";
    }

    /**
     *  记录日志用 dir 和filename 重复了
     * @param dir
     * @param fileName
     * @param content
     */
    public static void recorMsgd(String dir,String fileName, String content){
        try {
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
                new File(fileName);
            }
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write("\r\n"+content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
