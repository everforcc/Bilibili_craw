package cc.utils.impl;

import cc.utils.IFile;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author everforcc 2021-09-06
 */
@Slf4j
public class InputStreamUtils implements IFile {

    public void downFile(InputStream inputStream, String filePath, String fileName, BigDecimal fileLength){
        try {
            commonDownFile(inputStream,filePath,fileName,fileLength);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    private static void commonDownFile(InputStream in,String filePath,String fileName,BigDecimal fileLength)throws Exception{
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
            log.info( "文件已经存在:" + filePath + fileName );
            return;
        }
        log.info("开始下载");
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
        Date tempBegindate = new Date();
        boolean flag = fileLength.compareTo(new BigDecimal(0))>0;
        BigDecimal percent = fileLength.divide(new BigDecimal(1000));
        while ((length = in.read(buf, 0, buf.length)) != -1) {
            if(flag) {
                tempLength = tempLength.add(new BigDecimal(length));
                // 每 1% 跳出一行数据
                tempRate = new BigDecimal(df.format(tempLength.multiply(new BigDecimal(100)).divide(fileLength, 1, BigDecimal.ROUND_DOWN)));
                if (tempRate.compareTo(rate) == 1) {
                    log.info(fileName + ":" + tempRate + "%");
                    rate = tempRate;
                    Date tempEnddate = new Date();
                    double tempTime = tempEnddate.getTime() - tempBegindate.getTime();
                    BigDecimal bigDecimal = percent.divide(new BigDecimal(tempTime / 1000),2, BigDecimal.ROUND_HALF_UP);
                    log.info("耗时：" + tempTime / 1000 + "s" + ",速度:" + calSize(bigDecimal) + "/s" );
                    tempBegindate = new Date();
                }
            }
            fo.write(buf, 0, length);

        }
        in.close();
        fo.close();
        log.info(filePath+fileName + "下载完成");
        /**
         * 计算下载所用时间
         */
        Date enddate = new Date();
        double time = enddate.getTime() - begindate.getTime();
        // println.println(fileLength.divide(new BigDecimal(1024),2, BigDecimal.ROUND_HALF_UP)+"");
        // 也可以进一步优化，比如速度超过1024优化为b,kb,M，G等  1024~n 次方  有个方法找出接近2的n次方 hashmap     可以用递归取结果的方式 =0 i 累加
        BigDecimal bigDecimal = fileLength.divide(new BigDecimal(time / 1000),2, BigDecimal.ROUND_HALF_UP);
        log.info("耗时：" + time / 1000 + "s" + ",速度:" + calSize(bigDecimal) + "/s" );
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

}
