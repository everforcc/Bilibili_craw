package cc.exception;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
public interface ECode {

    String code();

    String msg();

    default boolean codeExceptionMsg() {
        throw new CodeException(msg());
    }

    /**
     * 直接抛出错误
     */
    default void wrong() {
        error(true);
    }

    /**
     * 抛出自定义错误信息
     */
    default void wrong(String msg) {
        error(true, msg);
    }

    /**
     * eg: 正确情况是200，就该写 !"200".equals(msg)
     *
     * @param flag
     */
    default void error(boolean flag) {
        if (flag) {
            throw new CodeException(msg());
        }
    }

    default void error(boolean flag, String msg) {
        if (flag) {
            throw new CodeException(msg() + msg);
        }
    }

}
