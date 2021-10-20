package ui.utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class PropertiesURL {

    // 配置文件根目录
    private static String path = "/swing/";
    public static void main(String[] args) {
        //getMap();
    }
    public static URL getURL(String resource){
        // 原项目放在代码目录中，无法编译，统一放到resources下
        return PropertiesURL.class.getResource(path + resource);
    }

    public static URL getURL(String relativePath, Class baseClass){
        String packageName = baseClass.getPackage().getName();
        System.out.println(packageName);
        packageName = packageName.substring(packageName.lastIndexOf(".")+1);
        System.out.println(packageName);
        // 原项目放在代码目录中，无法编译，统一放到resources下
        String source = path + packageName + "/" + relativePath;
        System.out.println(source);
        return PropertiesURL.class.getResource(source);
    }

}
