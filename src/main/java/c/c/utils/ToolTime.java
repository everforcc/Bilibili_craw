package c.c.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;


/**
 * 时间工具类
 */
public class ToolTime {

    /**
     * 根据正则返回时间
     * @param regex
     * @return
     */
    public static String nowTime(String regex) {
        return new SimpleDateFormat(regex).format(new Date(System.currentTimeMillis()));
    }

    public static String nowTime(long timeMillis) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(timeMillis));
    }

    public static String nowTime() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
    }
}
