package cc.service.ep.dto;

import cc.utils.vo.CommonVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author everforcc 2021-10-09
 */
@Getter
@Setter
public class EPVideoVO extends CommonVO {

    private String h1Title;

    private List<ep> epList;

    @Getter
    @Setter
    public static class ep{
        private String id;
        private String aid;
        private String bvid;
        private String cid;
        private String cover;
        private String title;
        private String titleFormat;
        private String longTitle;
    }

}
