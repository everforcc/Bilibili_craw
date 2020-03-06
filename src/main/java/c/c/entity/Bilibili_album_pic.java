package c.c.entity;

public class Bilibili_album_pic {

    private int id;
    private String doc_id ;
    private String poster_uid ;
    private String img_src ;
    private String img_width ;
    private String img_height ;
    private String img_size ;
    private int effect;

    public Bilibili_album_pic() {
    }

    public Bilibili_album_pic(String doc_id, String poster_uid, String img_src, String img_width, String img_height, String img_size) {
        this.doc_id = doc_id;
        this.poster_uid = poster_uid;
        this.img_src = img_src;
        this.img_width = img_width;
        this.img_height = img_height;
        this.img_size = img_size;
    }

    public Bilibili_album_pic(int id, String doc_id, String poster_uid, String img_src, String img_width, String img_height, String img_size, int effect) {
        this.id = id;
        this.doc_id = doc_id;
        this.poster_uid = poster_uid;
        this.img_src = img_src;
        this.img_width = img_width;
        this.img_height = img_height;
        this.img_size = img_size;
        this.effect = effect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getPoster_uid() {
        return poster_uid;
    }

    public void setPoster_uid(String poster_uid) {
        this.poster_uid = poster_uid;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public String getImg_width() {
        return img_width;
    }

    public void setImg_width(String img_width) {
        this.img_width = img_width;
    }

    public String getImg_height() {
        return img_height;
    }

    public void setImg_height(String img_height) {
        this.img_height = img_height;
    }

    public String getImg_size() {
        return img_size;
    }

    public void setImg_size(String img_size) {
        this.img_size = img_size;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }
}
