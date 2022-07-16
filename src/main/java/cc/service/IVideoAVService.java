package cc.service;

import cc.enums.VideoType;

public interface IVideoAVService {

    void flv(String input, String constantQuality);

    void mp4(String input);

    void flow(String input, String constantQuality, VideoType type);

    void m4s();

}
