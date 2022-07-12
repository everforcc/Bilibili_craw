package cc.exception;

import cc.enums.CodeExceptionEnum;
import org.junit.Test;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
public class CodeExceptionEnumTest {

    @Test
    public void t1(){
        CodeExceptionEnum.AID_NULL.error("aid".equals("aid"));
        System.out.println(1);
        CodeExceptionEnum.AID_NULL.error("".equals("aid"));
        System.out.println(2);
    }

}
