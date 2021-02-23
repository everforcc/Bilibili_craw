package c.c.thread;

import c.c.utils.Print_Record;

import java.util.List;

public class ThreadGroupDownObj {
    private static Print_Record println = Print_Record.getInstanse("");
    // 拿到全部数据做线程池
    /**
     * 十个一组来操作下载
     */

    private static List<String[]> strAryList;
    // 下载队列的数量
    private static int downQueue = 10;
    private static ThreadGroup downGroup = new ThreadGroup("down");
    public ThreadGroupDownObj(List<String[]> strAryList) {
        this.strAryList = strAryList;
    }

        public  void run(){
            int keepActive= 10;
            if(null!=strAryList&&strAryList.size()!=0){
               if(strAryList.size()<=downQueue){
                   strAryList.forEach(ThreadGroupDownObj::down);
                   while(downGroup.activeCount() > 0) {

                   }
               } else {
                   for(int i=0;i<downQueue;i++){
                       down(strAryList.get(i));
                   }
                   while(downGroup.activeCount() > 0) {
                       while (downGroup.activeCount()<downQueue && keepActive<strAryList.size() ){
                           println.println("下载队列增加新文件:"+strAryList.get(keepActive)[2]);
                           down(strAryList.get(keepActive));
                           keepActive++;
                       }
                   }
               }
            }else {
                println.println(" 没有可以下载的集合 ");
            }
        }

    private static void down(String[] strAry){
        ThreadDownFlv threadDownFlv = new ThreadDownFlv(strAry[0], strAry[1], strAry[2], strAry[3], strAry[4]);
        // 证明一个对象
        new Thread(downGroup, threadDownFlv, strAry[1]).start();
    }

    /*private static List<String[]> splitList(List<String[]> strAryListLocal){
        List<String[]> resultList = new ArrayList<>();
        if(strAryListLocal.size()>10){
           for(int i=0;i<downQueue;i++){
               resultList.add(strAryList.get(0));
               strAryListLocal.remove(0);
           }
           return resultList;
        }else {
           return strAryListLocal;
        }
    }*/
}
