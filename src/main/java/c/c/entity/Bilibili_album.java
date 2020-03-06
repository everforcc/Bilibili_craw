package c.c.entity;

public class Bilibili_album {

    private int id;
    private String album;
    private String poster_uid;
    private String title;
    private String description;
    private String count;
    private String ctime;
    private String view;
    private String like;
    private int effect;

    public Bilibili_album() {
    }

    public Bilibili_album(String album, String poster_uid, String title, String description, String count, String ctime, String view, String like) {
        this.album = album;
        this.poster_uid = poster_uid;
        this.title = title;
        this.description = description;
        this.count = count;
        this.ctime = ctime;
        this.view = view;
        this.like = like;
    }

    public Bilibili_album(int id, String album, String poster_uid, String title, String description, String count, String ctime, String view, String like, int effect) {
        this.id = id;
        this.album = album;
        this.poster_uid = poster_uid;
        this.title = title;
        this.description = description;
        this.count = count;
        this.ctime = ctime;
        this.view = view;
        this.like = like;
        this.effect = effect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPoster_uid() {
        return poster_uid;
    }

    public void setPoster_uid(String poster_uid) {
        this.poster_uid = poster_uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }
}
