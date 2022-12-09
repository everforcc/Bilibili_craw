/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 16:51
 * Copyright
 */

package cc.controller;

import cc.service.album.service.IUPAlbumService;
import cc.service.album.service.impl.UPAlbumServiceImpl;

/**
 * up相册
 */
public class UPAlbumController {

    public void upAlbum(String input) {
        IUPAlbumService iupAlbumService = new UPAlbumServiceImpl();
        iupAlbumService.downUPAlbum(input);
    }

}
