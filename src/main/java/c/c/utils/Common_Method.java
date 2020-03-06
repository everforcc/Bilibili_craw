package c.c.utils;

/**
 * Yukino
 * 2020/3/3
 */
public class Common_Method {

    /**
     * 插入数据库报错
     * 替换四个字节的字符 '\xF0\x9F\x98\x84\xF0\x9F）的解决方案
     * @param content
     * @return
     */
    public String removeFourChar(String content) {
        byte[] conbyte = content.getBytes();
        for (int i = 0; i < conbyte.length; i++) {
            if ((conbyte[i] & 0xF8) == 0xF0) {
                for (int j = 0; j < 4; j++) {
                    conbyte[i+j]=0x30;
                }
                i += 3;
            }
        }
        content = new String(conbyte);
        return content.replaceAll("0000", "*");
    }

}
