/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 16:19
 * Copyright
 */

package cc.controller;

import cc.service.cover.service.IAVCoverService;
import cc.service.cover.service.impl.AVCoverServiceImpl;

/**
 * 封面
 */
public class VideoCoverController {

    IAVCoverService iavCoverService = new AVCoverServiceImpl();

    public void dCover(String input){
        iavCoverService.downCover(input);
    }

}
