package cc.busi.ep.constant;

/**
 * @author everforcc 2021-10-09
 */
public class ConstantEPFlvURL {

    public static String rootUrl = "https://www.bilibili.com/bangumi/play/%s";

    /**
     * ep
     * aid
     * bvid
     * cid
     * qn
     * %% 代替%
     */
    public static String epUrlPlayurl = "https://api.bilibili.com/pgc/player/web/playurl?fnver=0&player=1&fnval=0&otype=json&ep%%5Fid=%s&avid=%s&bvid=%s&cid=%s&qn=%s";

    // String regex = "\\<script\\>window\\.__INITIAL_STATE__=.*;\\(function\\(\\)\\{var s;\\(s=document\\.currentScript\\|\\|document\\.scripts\\[document\\.scripts\\.length-1\\]\\)\\.parentNode\\.removeChild\\(s\\);\\}\\(\\)\\);\\</script\\>" ;
    private static final String regexStart = "\\<script\\>window\\.__INITIAL_STATE__=";
    private static final String regexEnd = ";\\(function\\(\\)\\{var s;\\(s=document\\.currentScript\\|\\|document\\.scripts\\[document\\.scripts\\.length-1\\]\\)\\.parentNode\\.removeChild\\(s\\);\\}\\(\\)\\);\\</script\\>";
    public static String regex = regexStart + "(.*)" + regexEnd;

}
