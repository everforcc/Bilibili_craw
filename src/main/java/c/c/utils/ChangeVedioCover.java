package c.c.utils;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Currency;

public class ChangeVedioCover {
    public static void main(String[] args) {
         addVideoCoverByPath(new File("E:\\test\\github\\视频\\22226490_不婼\\【不婼】书记舞！没有蟑螂但我有物理和灵魂丢帽呀"));  // ffmpeg -i "E:\test\github\视频\1_bishi\1.flv" "E:\test\github\视频\1_bishi\1.mp4"
        // E:\test\github\ffmpeg\鹿鸣>ffmpeg -i 2.mp4 -s 1920x1080 -pix_fmt yuv420p -vcodec libx264 -preset medium -profile:v high -level:v 4.1 -crf 23 -acodec aac -ar 44100 -ac 2 -b:a 128k out2.mp4
        //execCmd("E:\\test\\github\\视频\\1_bishi"," ffmpeg -i 4.flv 44.mp4");
        //String.format(COMMAND_TRANSCODE," 1.flv "," 1.mp4 ");
        // execCmd("E:\\test\\github\\视频\\1_bishi",String.format(COMMAND_TRANSCODE," 1.flv "," 1.mp4 "));
        try {
            //executeProcess("E:\\test\\github\\视频\\1_bishi",String.format(COMMAND_TRANSCODE," 1.flv "," 1.mp4 "));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Print_Record print_record = Print_Record.getInstanse("");
    public static final String FFMPEG_PATH = "D:\\environment\\ffmpeg\\bin\\ffmpeg.exe"; // ffmpeg 程序迷路
    public static final String COMMAND_RECOVER = "%s -i %s -i %s -map 1 -map 0 -c copy -disposition:0 attached_pic -y %s"; // ffmpeg 替换封面的命令
    public static final String COMMAND_TRANSCODE = "ffmpeg -i %s -s 1920x1080 -pix_fmt yuv420p -vcodec libx264 -preset medium -profile:v high -level:v 4.1 -crf 20 -acodec aac -ar 44100 -ac 2 -b:a 128k  %s"; // ffmpeg 替换封面的命令

    /*
       1.给定目录的方法
       2.如果是视频，那么截取第一帧的图片作为封面，文件名和视频名相同.png
       3.合并视频和文件
               */
    public static void addVideoCoverByPath(File fileRote){
        print_record.println(fileRote.getAbsolutePath());
        File[] files = fileRote.listFiles();
        for(File file:files){
            if(file.isFile()){
                // 判断文件类型
                createFileCover(new File(file.getAbsolutePath()));
            }
            // 是文件夹就进入
            if(file.isDirectory()){
                addVideoCoverByPath(new File(file.getAbsolutePath()));
            }
        }
    }

    public static void createFileCover(File file) {
        if(file.getName().endsWith("flv")){ // 目前下载的都是flv的
            // 改变文件类型
            // E:\test\github\ffmpeg\鹿鸣>ffmpeg -i 1.mp4 output.
            // E:\test\github\ep> ffmpeg -i ep.flv ep.flv.mp4
            //String cmd = file.getParent() + " ffmpeg -i " + file.getName() + " " + file.getName() + ".mp4" ;
            String cmd = String.format(COMMAND_TRANSCODE," \"" + file.getAbsolutePath() + "\"","\"" + file.getAbsolutePath() + ".mp4" + "\"" );
            print_record.println(file.getAbsolutePath()+"开始转码");
            execCmd(cmd);
            try {
                Thread.sleep(1000); // 这里太快的话，视频还没组装完毕，就会g
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createFileCover(new File(file.getAbsolutePath()+".mp4"));
        }/*else if(file.getName().endsWith("mp4")){
            videoImage(file.getAbsolutePath(),file.getParent(),file.getName());
            // 判断是否生成
            if(new File(file.getAbsolutePath()+".png").exists()){
                // 合并文件
                print_record.println(file.getAbsolutePath()+"合并视频封面:");
                String cmd = String.format(COMMAND_RECOVER, FFMPEG_PATH, file.getAbsolutePath(), file.getAbsolutePath()+".png", file.getParent()+"new"+file.getName());
                execCmd(cmd);
            }
        }*/else {
            // 不是的话不需要操作
            return;
        }

    }

    /**
     * 截取视频第六帧的图片
     * @param filePath 视频路径
     * @param dir 文件存放的根目录
     * @return 图片的相对路径 例：pic/1.png
     * @throws FrameGrabber.Exception
     */
    public static String videoImage(String filePath, String dir,String name) {
        String pngPath = "";
        FFmpegFrameGrabber ff = null;
        try {
            ff = FFmpegFrameGrabber.createDefault(filePath);
            ff.start();
            int ffLength = ff.getLengthInFrames();
            print_record.println("视频帧数ffLength:" + ffLength);
            Frame f = null;
            int i = 0;
            while (i < ffLength) {
                f = ff.grabImage();
                //截取第6帧
                if((i>0) &&  (f.image != null)){
                //if (f.image != null) {
                    //生成图片的相对路径 例如：pic/uuid.png
                    //pngPath = getPngPath();
                    pngPath = name + ".png";
                    //执行截图并放入指定位置
                    doExecuteFrame(f, dir + File.separator + pngPath);
                    break;
                }
                i++;
            }
            ff.stop();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        return pngPath;
    }
    /**
     * 截取缩略图
     * @param f Frame
     * @param targerFilePath:封面图片存放路径
     */
    private static void doExecuteFrame(Frame f, String targerFilePath) {
        String imagemat = "png";
        if (null == f || null == f.image) {
            return;
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bi = converter.getBufferedImage(f);
        File output = new File(targerFilePath);
        try {
            ImageIO.write(bi, imagemat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void execCmd(String cmd) {
        print_record.println("cmd: " + cmd);
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/C", cmd);
        Process process = null;
        try {
            process = builder.redirectErrorStream(true).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream in = process.getInputStream();
        outStream(in);
    }

    private static void outStream(InputStream in) {
        // 用一个读输出流类去读
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        // 逐行读取输出到控制台
        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String precessType="cmd /c ";
    public static void executeProcess(String path,String command) throws Exception{
        System.out.println(path+command);
        Process p;
        Runtime runtime =   Runtime.getRuntime();
        p = runtime.exec(precessType + command ,null,new File(path));
        System.out.println("执行命令返回:" +  p.getInputStream());
        /*System.out.println("休眠一秒");
        Thread.sleep(1000);
        System.out.println("runtime.gc();主动清理垃圾");*/
        runtime.gc();
        //System.out.println("p.destroy();强行终止当前进程");
        p.destroy();
    }

    /*private static void outStream(InputStream in) {
        // 用一个读输出流类去读
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in,"GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String line;
        // 逐行读取输出到控制台
        try {
            while ((line = br.readLine()) != null) {
                print_record.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
