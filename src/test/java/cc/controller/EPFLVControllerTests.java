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
     */
    @Test
    public void allEp() {
        String input = "ep7823";
        epflvController.dEp(input);
    }

    @Test
    public void oneEp() {
        String input = "ep7823";
        epflvController.dEp(input);
    }

}
