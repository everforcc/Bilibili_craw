package cc.vo;

import lombok.*;

import java.util.List;

/**
 * @author everforcc 2021-10-09
 */
@Getter
@Setter
public class BAlbumVO extends JSONString{

    private String upid;

    private Count count;

    private List<Doc> docList;

    @Getter
    @Setter
    public static class Count{
        // 总数
        private String all_count;
        private String draw_count;
        private String photo_count;
        private String daily_count;
    }

    @Getter
    @Setter
    public static class Doc{
        private String doc_id;
        private String poster_uid;
        private String title;
        private String description;

        private String count;
        private String ctime;
        private String view;
        private String like;
        private List<Picture> pictures;

        @Getter
        @Setter
        public class Picture{
            private String img_src;
            private String img_width;
            private String img_height;
            private String img_size;
        }
    }

}
