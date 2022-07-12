/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 16:35
 * Copyright
 */

package cc.service.impl;

import cc.busi.album.dto.BAlbumVO;
import cc.busi.album.flow.impl.UPAlbum;
import cc.service.IUPAlbumService;

public class UPAlbumServiceImpl implements IUPAlbumService {
    @Override
    public void downUPAlbum(String input) {
        UPAlbum upAlbum = new UPAlbum();
        // 1.
        BAlbumVO bAlbumVO = upAlbum.countByupid(input);
        // 2.
        upAlbum.allMsg(bAlbumVO);
        // 3. 下载文件
        upAlbum.downMsg(bAlbumVO);
        // 4.
        upAlbum.saveJson(bAlbumVO);
        // 5.
        System.out.println(bAlbumVO.toString());

    }
}
