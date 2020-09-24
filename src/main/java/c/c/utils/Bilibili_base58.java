package c.c.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Yukino
 * 2020/4/21
 */
public class Bilibili_base58 {

    /**
     * 参考文档https://www.zhihu.com/question/381784377/answer/1099438784
     * 把python抄过来java版本的
     */

    static String table="fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF";
    static Map<String,Integer> tr = new HashMap<String,Integer>();
    static Integer s[] = {11,10,3,8,4,6};
    static long xor =  177451812;
    static long add = 8728348608l  ;
    static List<String> r = new ArrayList<String>();

    static {
        //初始化tr
        for(int i=0;i<58;i++){
            //System.out.println("i:"+i+",table:"+table.substring(i,i+1));
            tr.put(table.substring(i,i+1),i);
        }
        //BV1wp4y1e76h
        r.add("B");
        r.add("V");
        r.add("1");
        r.add("");
        r.add("");
        r.add("4");
        r.add("");
        r.add("1");
        r.add("");
        r.add("7");
        r.add("");
        r.add("");
    }

    public static long power(long n,long m){
        long result=1;
        for(int i=0;i<m;i++){
              result*=n;
        }
        return result;
    }


    // BV >> AV
    public static long dec(String x){
          long r = 0 ;      //超长问题，int最大值 ，最好选用long不会有问题
          for(int i=0;i<6;i++){

              //Math.pow(tr.get(x.substring(s[i],s[i]+1))*58,i);// 但是这个方法有问题会有小数位
              r += tr.get(x.substring(s[i],s[i]+1))*power(58,i);

             /* System.out.println(i);
              System.out.println(s[i]);
              System.out.println(tr.get(x.substring(s[i],s[i]+1)));
              //System.out.println(tr.get(x.substring(s[i],s[i]+1))*58);
              System.out.println(tr.get(x.substring(s[i],s[i]+1))*power(58,i));*/

              //System.out.println(r);
          }
         return  (r-add)^xor;
    }

    public static String enc(long x){
         x = (x^xor)+add;
         for(int i=0;i<6;i++){
               long index = x/power(58,i)%58;
               r.set(s[i],table.substring((int)index,(int)(index+1)));
         }

               r.toString();
        //String join = String.join("-", r);

        return String.join("", r);
    }


    public static void main(String[] args) {

        System.out.println(dec("BV17x411w7KC"));
        //BV1wp4y1e76h
        System.out.println(dec("BV1wp4y1e76h"));
        System.out.println(enc(170001));
    }
}
