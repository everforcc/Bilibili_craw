/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-12 16:57
 * Copyright
 */

package cc.service.ep.service.impl;

import cc.service.ep.flow.EPFLV;
import cc.utils.busi.DownMsg;
import cc.service.ep.service.IEPFLVService;

import java.util.List;

public class EPFLVServiceImpl implements IEPFLVService {
    @Override
    public void dEp(String input) {
        EPFLV epflv = new EPFLV();
        // 1. 校验
        String ep = input;
        // 2.获得html
        String html = epflv.getHTMLByep(ep);
        // 3.正则匹配到js
        String json = epflv.matchJSON(ep, html);
        // 4. 拿到url组装下载信息
        List<DownMsg> downMsgList = epflv.getFileMsg(ep, json);
        // 5. down
        epflv.downFile(downMsgList);
    }

    @Override
    public void dEp(String input, int index) {
        EPFLV epflv = new EPFLV();
        // 1. 校验
        String ep = input;
        // 2.获得html
        String html = epflv.getHTMLByep(ep);
        // 3.正则匹配到js
        String json = epflv.matchJSON(ep, html);
        // 4. 拿到url组装下载信息
        List<DownMsg> downMsgList = epflv.getFileMsg(ep, json);
        // 5. down
        epflv.downFile(downMsgList, index);
    }

    @Override
    public void dEp(String input, int start, int end) {
        EPFLV epflv = new EPFLV();
        // 1. 校验
        String ep = input;
        // 2.获得html
        String html = epflv.getHTMLByep(ep);
        // 3.正则匹配到js
        String json = epflv.matchJSON(ep, html);
        // 4. 拿到url组装下载信息
        List<DownMsg> downMsgList = epflv.getFileMsg(ep, json);
        // 5. down
        epflv.downFile(downMsgList, start, end);
    }
}
