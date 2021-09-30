package cc.exception;

import cc.enums.CodeEnum;
import org.junit.Test;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
public class CodeEnumTest {

    @Test
    public void t1(){
        CodeEnum.AID_NULL.isEffect("aid".equals("aid"));
        System.out.println(1);
        CodeEnum.AID_NULL.isEffect("".equals("aid"));
        System.out.println(2);
    }

}
