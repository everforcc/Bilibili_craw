/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 17:01
 * Copyright
 */

package cc.controller;

import org.junit.Test;

public class EPFLVControllerTests {

    EPFLVController epflvController = new EPFLVController();

    /**
     * todo 待完善校验
     * ,目录还有有点问题
     */
    @Test
    public void allEp() {
        String input = "ep468833";
        epflvController.dEp(input);
    }

    @Test
    public void oneEp() {
        // 东周列国·春秋篇 ep468833
        String input = "ss34228";
        epflvController.dEp(input, 1);
    }

    @Test
    public void oneEpList() {
        // 三国演义 ss33626
        String input = "ss33626";
        epflvController.dEp(input, 1, 10);
    }

}
