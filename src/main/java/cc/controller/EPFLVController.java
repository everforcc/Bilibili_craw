/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 17:00
 * Copyright
 */

package cc.controller;

import cc.service.IEPFLVService;
import cc.service.impl.EPFLVServiceImpl;

public class EPFLVController {

    IEPFLVService iepflvService = new EPFLVServiceImpl();

    public void dEp(String input){
        iepflvService.dEp(input);
    }

}
