import c.c.utils.IpProxyUtils;
import org.junit.Test;

import java.util.Date;

/**
 * Yukino
 * 2020/3/10
 */
public class T_Proxy {


    IpProxyUtils ipProxyUtils = new IpProxyUtils();

    @Test
    public void isValid(){
        for(int i=1;i<+10;i++) {
            System.out.println("第"+i+"次");
            String ip = "121.239.208.184";
            int port = 8118;
            int code = ipProxyUtils.isValid(ip, port);
            System.out.println(code);
            if (code == 200) {
                System.out.println("ip:" + ip + "port:" + port + ",可用");
                return;
            } else {
                System.out.println("ip:" + ip + "port:" + port + ",不可用");
            }
        }
    }

    @Test
    public void getMyIp(){
        Date startDate = new Date();
        double a = 1;
        double b = 0;
        for(int i=1;i<=100;i++) {
            if(ipProxyUtils.getMyIp().equals("0")){
                System.out.println( "a:" + a++ +"----------------------");
            }else{
                System.out.println("成功了"+ ++b +"次");
            }
            System.out.println( "i:" + i +"----------------------");
        }
        //if(b!=0) {
        double c =  b /  100;
            System.out.println(c);
            System.out.println("成功率:" + b + "%");
        //}

        Date endDate = new Date();
        double time = endDate.getTime() - startDate.getTime();
        System.out.println("耗时：" + time / 1000 + "s");
    }
}
