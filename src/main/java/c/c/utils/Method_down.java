package c.c.utils;

import c.c.utils.Constant;

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

    private static Print_Record println = Print_Record.getInstanse("");

    // 视频大小
    //private BigDecimal fileLength = new BigDecimal(0);

    /**
     * 下载自定义文件名称
     * @param url
     * @param fileName
     * @throws Exception
     */
    public static void downByUrl(String url,String dir,String fileName) throws Exception{
        URL uri = new URL(url);
        InputStream in = uri.openStream();
        file(uri.openStream(),Constant.rootFilePath+dir+"\\",fileName,new BigDecimal(1));
    }

    /**
     *
     * 根据网络路径命名
     * @param urlPath
     * @param dir
     * @throws Exception
     */
    public static void down(String urlPath,String dir){
        /**
         * 截取网络图片的名字和参数
         */
        String imageName = urlPath.substring(urlPath.lastIndexOf("/") + 1, urlPath.length());
        println.println("生成文件名:" + imageName);
        if (imageName.contains("?")) {
            println.println("处理字符串");
            imageName = imageName.substring(0, imageName.lastIndexOf("@"));
            println.println("再次生成文件名" + imageName);
        }
        URL uri = null;
        try {
            uri = new URL(urlPath);
        } catch (MalformedURLException e) {
            println.printErrln("URL错误" + e.toString());
            e.printStackTrace();
        }
        try {

            file(uri.openStream(),Constant.rootFilePath+dir+"\\",imageName,new BigDecimal(1));
        } catch (Exception e) {
            println.printErrln("URL.openStream错误" + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 下载视频，需要请求头
     * @param flvUrl
     * @param aid
     * @param dir
     * @param fileName
     * @throws Exception
     */
    public static void downFlv(String flvUrl,String aid,String dir,String fileName,String requestMethod)throws Exception{

        /**
         * 根据地址去请求获取下载视频的链接
         */
        HttpURLConnection conn = Request_Heard.requestHeard_downFlv(flvUrl,"av"+aid,requestMethod);
        BigDecimal fileLength = new BigDecimal(conn.getContentLength());
        println.println("视频大小:"+conn.getContentLength());
        // 文件如何拆分的问题，估计最后还是的计算，但是结果可以就好
        // 增加1023 加载自身一位正好1 k
        // 可以分批下载到本地再合并，多个线程
        /*for(int i=0;i<38287700;i++) {
            conn = Request_Heard.requestHeard_downFlvBySplit(flvUrl,"av"+aid,requestMethod,"bytes=" + i +"-"+(i+=1023) );
            file(conn.getInputStream(), Constant.rootFilePath + dir + "\\", i + fileName);
        }*/
        file(conn.getInputStream(), Constant.rootFilePath + dir + "\\",  fileName,fileLength);
    }

    public static void rename(String oldName,String newName){
        File file = new File(Constant.rootFilePath+oldName);
        file.renameTo(new File(Constant.rootFilePath+newName));
        println.println(Constant.rootFilePath+oldName + "改名为:" + Constant.rootFilePath+newName);
    }

    /**
     * 用来下载文件
     * @param in
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    private static void file(InputStream in,String filePath,String fileName,BigDecimal fileLength)throws Exception{
        // 总时间
        Date begindate = new Date();

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
        println.println("耗时：" + time / 1000 + "s" + ",速度:" + fileLength.divide(new BigDecimal(1024)).divide(new BigDecimal(time / 1000),2, BigDecimal.ROUND_HALF_UP) + "kb/s" );
    }

    public static void record(String dir,String fileName, String content){
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
