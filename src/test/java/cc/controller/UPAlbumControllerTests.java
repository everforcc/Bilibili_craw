/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 16:52
 * Copyright
 */

package cc.controller;

import org.junit.Test;

public class UPAlbumControllerTests {

    UPAlbumController upAlbumController = new UPAlbumController();

    /**
     * todo 待完善校验
     * 相册的路径和其他的路径都要再说
     */
    @Test
    public void dAVFlv() {
        String uid = "396441809";
        upAlbumController.upAlbum(uid);
    }


}
