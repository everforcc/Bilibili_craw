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

    /**
     * 请使用第一集的id
     *
     * @param input 第一集的id
     */
    public void dEp(String input) {
        iepflvService.dEp(input);
    }

    public void dEp(String input, int index) {
        iepflvService.dEp(input, index);
    }

    public void dEp(String input, int start, int end) {
        iepflvService.dEp(input, start, end);
    }

}
