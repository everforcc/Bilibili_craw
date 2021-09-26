package cc.utils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
public class ZipUtils {

    // 用来处理gzip速度的问题，参考Novel_craw

    public static void main(String[] args) {
        ZipUtils zipUtils = new ZipUtils();
        try {
            zipUtils.zip("D:\\linshi\\a.zip",new File("D:\\linshi\\a.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName)); // 创建ZipOutputStream类对象
        zip(out, inputFile, "a.txt");//D:\linshi\a.zip 填空   解压后的文件名
        // 调用方法
        System.out.println("压缩中…"); // 输出信息
        out.close(); // 将流关闭
    }

    private void zip(ZipOutputStream out, File inputFile, String path) {

        if(inputFile.isDirectory()){

        }else {
            try {
                out.putNextEntry(new ZipEntry(path));

                FileInputStream fileInputStream = new FileInputStream(inputFile);
                int b; // 定义int型变量
                System.out.println(path);
                while ((b = fileInputStream.read()) != -1) {
                    // 如果没有到达流的尾部
                    out.write(b);
                    // 将字节写入当前ZIP条目
                }
                fileInputStream.close(); // 关闭流
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String gzipRestore(String string) throws IOException {
        // ByteArrayInputStream 内存溢出
        GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(string.getBytes()));
        return gzipRestore(gzip);
    }

    public static String gzipRestore(InputStream inputStream) throws IOException {
        // ByteArrayInputStream 内存溢出
        GZIPInputStream gzip = new GZIPInputStream(inputStream);
        return gzipRestore(gzip);
    }

    private static String gzipRestore(GZIPInputStream gzip) throws IOException {
        InputStreamReader isr = new InputStreamReader(gzip);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String temp;
        while((temp = br.readLine()) != null){
            sb.append(temp + "\r\n");
        }
        isr.close();
        gzip.close();
        return sb.toString();
    }

}
