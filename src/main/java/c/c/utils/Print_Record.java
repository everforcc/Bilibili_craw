package c.c.utils;

import java.io.File;

/**
 * Yukino
 * 2020/3/9
 */
public class Print_Record {

    /* 调整为输出加上 包名 类名，方法名 */

    private static Print_Record print_record;

    private String fileName;

    // private static String classname = new Exception().getStackTrace()[1].getClassName(); //获取调用者的类名
    // private static String method_name = new Exception().getStackTrace()[1].getMethodName(); //获取调用者的方法名

    private Print_Record(String fileName){
        this.fileName = Constant.rootFilePath + "log\\" + ToolTime.nowTime() + ".log" ;
    };

    public static synchronized  Print_Record getInstanse(String fileName){
        if(print_record == null){
            print_record = new Print_Record(fileName);
        }
        return print_record;
    }



    public void println(String msg){
        String location="";
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        // 这里还可以根据包名 来分类 保存日志
        location = "[["+stacks[2].getClassName() + "](" + stacks[2].getMethodName() + ")" + "" + stacks[2].getLineNumber() + "]";
        msg = ToolTime.nowTime("yyyy-MM-dd hh:mm:ss ")+" : " + location + " --- " +msg ;
        System.out.println( msg );

        Method_down.recorMsgd(Constant.rootFilePath + "log\\" ,fileName,msg);
    }

    public void printErrln(String msg){
        String location="";
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        // 这里还可以根据包名 来分类 保存日志
        location = "[["+stacks[2].getClassName() + "](" + stacks[2].getMethodName() + ")" + "" + stacks[2].getLineNumber() + "]";
        msg = ToolTime.nowTime("yyyy-MM-dd hh:mm:ss ")+" err : " + location + " --- " +msg ;
        System.err.println( msg );

        Method_down.recorMsgd(Constant.rootFilePath + "log\\" ,fileName,msg);
    }

}
