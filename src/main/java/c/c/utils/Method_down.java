package c.c.utils;

import c.c.utils.Constant;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Yukino
 * 2020/3/3
 */
public class Method_down {

    private static Print_Record println = Print_Record.getInstanse("");

    /**
     * 下载自定义文件名称
     * @param url
     * @param fileName
     * @throws Exception
     */
    public static void downByUrl(String url,String dir,String fileName) throws Exception{
        URL uri = new URL(url);
        InputStream in = uri.openStream();
        file(uri.openStream(),Constant.rootFilePath+dir+"\\",fileName);
    }

    /**
     *
     * 根据网络路径命名
     * @param urlPath
     * @param dir
     * @throws Exception
     */
    public static void down(String urlPath,String dir)throws Exception{
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
        URL uri = new URL(urlPath);
        file(uri.openStream(),Constant.rootFilePath+dir+"\\",imageName);
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

        file(conn.getInputStream(),Constant.rootFilePath+dir+"\\",fileName);
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
    private static void file(InputStream in,String filePath,String fileName)throws Exception{
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
            println.println("文件已经存在:"+fileName);
           return;
        }
        println.println("开始下载");
        FileOutputStream fo = new FileOutputStream(file);

        /**
         * 以流的方式进行下载
         */
        byte[] buf = new byte[1024];
        int length = 0;
        while ((length = in.read(buf, 0, buf.length)) != -1) {
            fo.write(buf, 0, length);
        }
        in.close();
        fo.close();
        println.println(fileName + "下载完成");
        /**
         * 计算下载所用时间
         */
        Date enddate = new Date();
        double time = enddate.getTime() - begindate.getTime();
        println.println("耗时：" + time / 1000 + "s");
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
