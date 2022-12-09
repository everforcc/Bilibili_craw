package cc.service.av.service;

import cc.enums.VideoType;

public interface IVideoAVService {

    /**
     * 下载flv格式的视频
     *
     * @param input           视频号
     * @param constantQuality 质量
     */
    void flv(String input, String constantQuality);

    /**
     * 下载 mp4 格式的视频
     * 默认 720p
     *
     * @param input 视频号
     */
    void mp4(String input);

    /**
     * 上面方法的结合
     *
     * @param input           视频号
     * @param constantQuality 质量
     * @param type            类型
     */
    void flow(String input, String constantQuality, VideoType type);

    /**
     * 将来 4k 视频用,暂时未实现
     */
    void m4s();

}
