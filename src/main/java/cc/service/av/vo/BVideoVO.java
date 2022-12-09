package cc.service.av.vo;

import cc.utils.vo.CommonVO;
import lombok.*;

import java.util.List;

/**
 * @author everforcc
 * @data 2021/9/3 0003
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BVideoVO extends CommonVO {

    /**
     * 短视频相关的实体类
     * av,bv开头
     */

    private String bvid;
    private String aid;
    private String pic;
    private String title;
    private OwnerVO owner;
    private List<CidVO> pages;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CidVO{
        private String cid;
        private String part;

        @Getter
        @Setter
        public static class DimensionVO{

        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OwnerVO{
        private String mid;
        private String name;
        private String face;
    }

}
