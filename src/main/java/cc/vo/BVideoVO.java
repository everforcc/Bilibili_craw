package cc.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 郭凯龙
 * @data 2021/9/3 0003
 */
@Data
public class BVideoVO {

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

    @Data
    public static class CidVO{
        private String cid;
        private String part;

        @Data
        public static class DimensionVO{

        }

    }

    @Data
    public static class OwnerVO{
        private String mid;
        private String name;
        private String face;
    }

}
