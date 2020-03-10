package c.c.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;

/**
 * 透明匿名问题
 * 1.找代理ip和port
 * 2.设置代理
 * 3.检测ip
 *
 * Yukino
 * 2020/3/2
 */
public class IpProxyUtils {

    String xcdlProxy = "https://www.xicidaili.com/";
    private static final String MY_IP_API = "https://www.ipip.net/ip.html";
    /**
     * 1. 爬取代理ip
     *
     *  https需要获取高匿ip
     * @throws Exception
     */
    public void crawl_http() throws Exception {
        //String html = HttpUtils.getResponseContent(api + index);
        //System.out.println(html);
        URL xcUrl = new URL(xcdlProxy);
        HttpURLConnection conn = (HttpURLConnection) xcUrl.openConnection();
        conn.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");

        Document document = Jsoup.parse(conn.getInputStream(),"UTF-8",xcdlProxy);

        Element element_home = document.getElementById("home");

        //System.out.println(element_home);

        Elements elements_home_tr = element_home.getElementsByTag("tr");

        //System.out.println(elements_home_tr.size());

        //System.out.println(elements_home_tr);
        //解析岀ip
        for(Element element_tr:elements_home_tr){
            //Elements elements_tr_th = element_tr.getElementsByTag("th");
            Elements elements_tr_td_ary = element_tr.getElementsByTag("td");
            //System.out.println("th:"+elements_tr_th.size()+",td:"+elements_tr_td.size());
            /*for(Element element_tr_td:elements_tr_td_ary){
                //<tr class="odd">
                // <td class="country"><img src="//fs.xicidaili.com/images/flag/cn.png" alt="Cn"></td>
                // <td>27.154.34.146</td>
                // <td>31527</td>
                // <td>福建厦门</td>
                // <td class="country">高匿</td>
                // <td>HTTPS</td>
                // <td>480天</td>
                // <td>30分钟前</td>
                //</tr>
                System.out.print(element_tr_td.text()+" ");
                //获取ip，存入数据库开始做代理
                //element_tr_td.getElementsByTag()
                }
            }*/
            /*for(int i=0;i<elements_tr_td_ary.size();i++){
                System.out.print(i+","+elements_tr_td_ary.get(i).text());
            }*/
            if(elements_tr_td_ary.size()>0){
                //System.out.println(1+","+elements_tr_td_ary.get(1).text());
                //System.out.println(2+","+elements_tr_td_ary.get(2).text());
                System.out.println(getMyIp(elements_tr_td_ary.get(1).text(), Integer.valueOf(elements_tr_td_ary.get(2).text())));
            }
            System.out.println();
            //System.out.print(elements_tr_td.get(2).text()+" "+elements_tr_td.get(3).text());
            //System.out.println("elements_tr_td:"+elements_tr_td.size());
        }
    }
/*******************************************************/
//检测ip是否有效


    /**
     * 2.获取当前ip 检测是否成功
     * @return
     */
    public String getMyIp(String proxyIp,int proxyPort) {
        try {
            HttpsURLConnection conn = null;

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, proxyPort));
            //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("183.48.35.21", 8118));
            //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("175.155.238.145", 1133));
            //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("117.88.176.215", 3000));
            conn = (HttpsURLConnection) new URL(MY_IP_API).openConnection(proxy);


            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{(TrustManager) new TrustAnyTrustManager()}, new java.security.SecureRandom());
            conn.setSSLSocketFactory(sslContext.getSocketFactory());
            conn.setHostnameVerifier((HostnameVerifier) new TrustAnyHostnameVerifier());

            conn.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            Document doc = Jsoup.parse(conn.getInputStream(),"UTF-8",MY_IP_API);

            //Document document = Jsoup.parse(conn.getInputStream(),"UTF-8",MY_IP_API);

            //String html = HttpUtils.getResponseContent(MY_IP_API);

            //Document doc = Jsoup.parse(html);
            Element element = doc.selectFirst("div.tableNormal");

            Element ele = element.selectFirst("table").select("td").get(1);

            String ip = element.selectFirst("a").text();

            System.out.println(ip);
            // System.out.println(ip);
            return ip;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        //return "0";
    }



    /**
     * 设置https代理
     * @throws Exception
     */
    public void HttpsProxy() throws Exception {
        HttpsURLConnection connection = null;

        String urlpath="https://www.baidu.com";
        // 设置代理

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("183.48.35.21", 8118));
        //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("175.155.238.145", 1133));
        //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("117.88.176.215", 3000));
            connection = (HttpsURLConnection) new URL(urlpath).openConnection(proxy);


                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{(TrustManager) new TrustAnyTrustManager()}, new java.security.SecureRandom());
                connection.setSSLSocketFactory(sslContext.getSocketFactory());
                connection.setHostnameVerifier((HostnameVerifier) new TrustAnyHostnameVerifier());



        if (connection == null) {
            connection = (HttpsURLConnection) new URL(urlpath).openConnection();
            System.out.println("if");
        }

        // 添加请求头部
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36");
        /*if (headerMap != null) {
            Iterator<Map.Entry<String, List<String>>> iterator = headerMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<String>> entry = iterator.next();
                List<String> values = entry.getValue();
                for (String value : values)
                    connection.setRequestProperty(entry.getKey(), value);
            }
        }*/

        InputStream inputStream = connection.getInputStream();

        Map headers = connection.getHeaderFields();
        Set<String> keys = headers.keySet();
        for( String key : keys ){
            String val = connection.getHeaderField(key);
            System.out.println(key+"    "+val);
        }

        connection.disconnect();
    }

    /**
     * 设置http代理
     * @param url
     * @param param
     * @param proxy
     * @param port
     * @return
     */
    public static String HttpProxy(String url, String param, String proxy, int port) {
        HttpURLConnection httpConn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        BufferedReader reader = null;
        try {
            URL urlClient = new URL(url);
            System.out.println("请求的URL========：" + urlClient);
            //创建代理
            Proxy proxy1=new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy, port));
            //设置代理
            httpConn = (HttpURLConnection) urlClient.openConnection(proxy1);
            // 设置通用的请求属性

            // 发送POST请求必须设置如下两行
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);

            httpConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            httpConn.setRequestProperty("Accept-Encoding", " gzip, deflate, br");
            httpConn.setRequestProperty("Accept-Language", " zh-CN,zh;q=0.9");
            httpConn.setRequestProperty("Connection", " keep-alive");
            httpConn.setRequestProperty("Host", "www.bilibili.com");
            httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            httpConn.setRequestMethod("GET");



            //httpConn.connect();
            // 设置通用的请求属性


            // 获取URLConnection对象对应的输出流
            OutputStream outputStream = httpConn.getOutputStream();
            out = new PrintWriter(outputStream);
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(httpConn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            //getMyIp();
            // 断开连接
            httpConn.disconnect();
            System.out.println("====result===="+result);
            System.out.println("返回结果：" + httpConn.getResponseMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (out != null) {
                out.close();
            }
        }

        return result;
    }

/*************************************************百度*********************************************************************/
    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


}


