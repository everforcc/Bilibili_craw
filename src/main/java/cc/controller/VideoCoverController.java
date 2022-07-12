/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 16:19
 * Copyright
 */

package cc.controller;

import cc.service.IAVCoverService;
import cc.service.impl.AVCoverServiceImpl;

public class VideoCoverController {

    IAVCoverService iavCoverService = new AVCoverServiceImpl();

    public void dCover(String input){
        iavCoverService.downCover(input);
    }

}
