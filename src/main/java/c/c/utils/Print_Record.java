package c.c.utils;

import java.io.File;

/**
 * Yukino
 * 2020/3/9
 */
public class Print_Record {

    private static Print_Record print_record;

    private String fileName;

    private Print_Record(String fileName){
        this.fileName = Constant.rootFilePath + "log\\" + ToolTime.nowTime() + "_" + fileName + ".log" ;
    };

    public static synchronized  Print_Record getInstanse(String fileName){
        if(print_record == null){
            print_record = new Print_Record(fileName);
        }
        return print_record;
    }


    public void println(String msg){
        Method_down.record(Constant.rootFilePath + "log\\" ,fileName,msg);
        System.out.println("通过帮助类输出----------");
        System.out.println(msg);
    }

}
