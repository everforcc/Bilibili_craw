package c.c.thread;

import c.c.utils.Method_down;
import c.c.utils.Print_Record;

public class ThreadDownFlv implements Runnable  {

    private String flvUrl;
    private String aid;
    private String dir;
    private String fileName;
    private String requestMethod;

    public String getFlvUrl() {
        return flvUrl;
    }

    public void setFlvUrl(String flvUrl) {
        this.flvUrl = flvUrl;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public ThreadDownFlv(String flvUrl, String aid, String dir, String fileName, String requestMethod) {
        this.flvUrl = flvUrl;
        this.aid = aid;
        this.dir = dir;
        this.fileName = fileName;
        this.requestMethod = requestMethod;
    }
    Print_Record println = Print_Record.getInstanse("") ;
   @Override
    public void run() {
       try {
           println.println("线程:" + aid + "开始运行");
           Method_down.downFlv(flvUrl, aid, dir, fileName, requestMethod);
       } catch (Exception e) {
           e.printStackTrace();
       }
       //super.run();
    }
}
