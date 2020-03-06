import c.c.utils.JsonHelp;
import org.junit.Test;

/**
 * Yukino
 * 2020/3/6
 */
public class T_JsonHelp {

    String json = "{\n" +
            "\t\"code\": 0,\n" +
            "\t\"message\": \"0\",\n" +
            "\t\"ttl\": 1,\n" +
            "\t\"data\": {\n" +
            "\t\t\"list\": {\n" +
            "\t\t\t\"tlist\": {\n" +
            "\t\t\t\t\"129\": {\n" +
            "\t\t\t\t\t\"tid\": 129,\n" +
            "\t\t\t\t\t\"count\": 63,\n" +
            "\t\t\t\t\t\"name\": \"舞蹈\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"155\": {\n" +
            "\t\t\t\t\t\"tid\": 155,\n" +
            "\t\t\t\t\t\"count\": 1,\n" +
            "\t\t\t\t\t\"name\": \"时尚\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"160\": {\n" +
            "\t\t\t\t\t\"tid\": 160,\n" +
            "\t\t\t\t\t\"count\": 6,\n" +
            "\t\t\t\t\t\"name\": \"生活\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"3\": {\n" +
            "\t\t\t\t\t\"tid\": 3,\n" +
            "\t\t\t\t\t\"count\": 1,\n" +
            "\t\t\t\t\t\"name\": \"音乐\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"5\": {\n" +
            "\t\t\t\t\t\"tid\": 5,\n" +
            "\t\t\t\t\t\"count\": 1,\n" +
            "\t\t\t\t\t\"name\": \"娱乐\"\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t\"vlist\": [{\n" +
            "\t\t\t\t\"comment\": 587,\n" +
            "\t\t\t\t\"typeid\": 199,\n" +
            "\t\t\t\t\"play\": 503297,\n" +
            "\t\t\t\t\"pic\": \"//i0.hdslb.com/bfs/archive/be946c44ecb23da7c57998644ac86860e53cf826.jpg\",\n" +
            "\t\t\t\t\"subtitle\": \"\",\n" +
            "\t\t\t\t\"description\": \"情人节的投稿来晚了！\\n不过还是要打扰你一下，请问可以去通往你心里路吗？\\n\\n摄影\\u0026后期：item道具\\n歌曲：AOA\",\n" +
            "\t\t\t\t\"copyright\": \"\",\n" +
            "\t\t\t\t\"title\": \"【青鸢】Excuse me❤️撩你一下\",\n" +
            "\t\t\t\t\"review\": 0,\n" +
            "\t\t\t\t\"author\": \"青鸢Cyan-\",\n" +
            "\t\t\t\t\"mid\": 11605312,\n" +
            "\t\t\t\t\"created\": 1581930052,\n" +
            "\t\t\t\t\"length\": \"01:37\",\n" +
            "\t\t\t\t\"video_review\": 378,\n" +
            "\t\t\t\t\"aid\": 89453968,\n" +
            "\t\t\t\t\"bvid\": \"\",\n" +
            "\t\t\t\t\"hide_click\": false,\n" +
            "\t\t\t\t\"is_pay\": 0,\n" +
            "\t\t\t\t\"is_union_video\": 0\n" +
            "\t\t\t}]\n" +
            "\t\t},\n" +
            "\t\t\"page\": {\n" +
            "\t\t\t\"count\": 72,\n" +
            "\t\t\t\"pn\": 1,\n" +
            "\t\t\t\"ps\": 1\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}" ;
    String cidJson="{\n" +
            "\t\"code\": 0,\n" +
            "\t\"message\": \"0\",\n" +
            "\t\"ttl\": 1,\n" +
            "\t\"data\": {\n" +
            "\t\t\"bvid\": \"\",\n" +
            "\t\t\"aid\": 84493326,\n" +
            "\t\t\"videos\": 2,\n" +
            "\t\t\"tid\": 20,\n" +
            "\t\t\"tname\": \"宅舞\",\n" +
            "\t\t\"copyright\": 1,\n" +
            "\t\t\"pic\": \"http://i0.hdslb.com/bfs/archive/d206212cbbb62dc28a819a7663c8d38fd7d48314.jpg\",\n" +
            "\t\t\"title\": \"“腾格尔”恋爱循环居然如此难顶！！【青鸢】\",\n" +
            "\t\t\"pubdate\": 1579683656,\n" +
            "\t\t\"ctime\": 1579631148,\n" +
            "\t\t\"desc\": \"谁偷换了我的bgm！！\\n\\n摄影后期：Dias\",\n" +
            "\t\t\"state\": 0,\n" +
            "\t\t\"attribute\": 8405376,\n" +
            "\t\t\"duration\": 196,\n" +
            "\t\t\"mission_id\": 12711,\n" +
            "\t\t\"rights\": {\n" +
            "\t\t\t\"bp\": 0,\n" +
            "\t\t\t\"elec\": 0,\n" +
            "\t\t\t\"download\": 1,\n" +
            "\t\t\t\"movie\": 0,\n" +
            "\t\t\t\"pay\": 0,\n" +
            "\t\t\t\"hd5\": 1,\n" +
            "\t\t\t\"no_reprint\": 1,\n" +
            "\t\t\t\"autoplay\": 1,\n" +
            "\t\t\t\"ugc_pay\": 0,\n" +
            "\t\t\t\"is_cooperation\": 0,\n" +
            "\t\t\t\"ugc_pay_preview\": 0,\n" +
            "\t\t\t\"no_background\": 0\n" +
            "\t\t},\n" +
            "\t\t\"owner\": {\n" +
            "\t\t\t\"mid\": 11605312,\n" +
            "\t\t\t\"name\": \"青鸢Cyan-\",\n" +
            "\t\t\t\"face\": \"http://i1.hdslb.com/bfs/face/3e193a763cd1d3049cabf3c9382ddd3ff5327e08.jpg\"\n" +
            "\t\t},\n" +
            "\t\t\"stat\": {\n" +
            "\t\t\t\"aid\": 84493326,\n" +
            "\t\t\t\"view\": 367894,\n" +
            "\t\t\t\"danmaku\": 691,\n" +
            "\t\t\t\"reply\": 457,\n" +
            "\t\t\t\"favorite\": 12054,\n" +
            "\t\t\t\"coin\": 6420,\n" +
            "\t\t\t\"share\": 761,\n" +
            "\t\t\t\"now_rank\": 0,\n" +
            "\t\t\t\"his_rank\": 0,\n" +
            "\t\t\t\"like\": 12419,\n" +
            "\t\t\t\"dislike\": 0,\n" +
            "\t\t\t\"evaluation\": \"\"\n" +
            "\t\t},\n" +
            "\t\t\"dynamic\": \"#春节拍摄大作战# #恋爱循环# 真好听呀真好听！你觉得呢\",\n" +
            "\t\t\"cid\": 144517225,\n" +
            "\t\t\"dimension\": {\n" +
            "\t\t\t\"width\": 1920,\n" +
            "\t\t\t\"height\": 1080,\n" +
            "\t\t\t\"rotate\": 0\n" +
            "\t\t},\n" +
            "\t\t\"no_cache\": false,\n" +
            "\t\t\"pages\": [{\n" +
            "\t\t\t\"cid\": 144517225,\n" +
            "\t\t\t\"page\": 1,\n" +
            "\t\t\t\"from\": \"vupload\",\n" +
            "\t\t\t\"part\": \"闹一闹\",\n" +
            "\t\t\t\"duration\": 98,\n" +
            "\t\t\t\"vid\": \"\",\n" +
            "\t\t\t\"weblink\": \"\",\n" +
            "\t\t\t\"dimension\": {\n" +
            "\t\t\t\t\"width\": 1920,\n" +
            "\t\t\t\t\"height\": 1080,\n" +
            "\t\t\t\t\"rotate\": 0\n" +
            "\t\t\t}\n" +
            "\t\t}, {\n" +
            "\t\t\t\"cid\": 144763807,\n" +
            "\t\t\t\"page\": 2,\n" +
            "\t\t\t\"from\": \"vupload\",\n" +
            "\t\t\t\"part\": \"不闹了乖\",\n" +
            "\t\t\t\"duration\": 98,\n" +
            "\t\t\t\"vid\": \"\",\n" +
            "\t\t\t\"weblink\": \"\",\n" +
            "\t\t\t\"dimension\": {\n" +
            "\t\t\t\t\"width\": 1080,\n" +
            "\t\t\t\t\"height\": 1920,\n" +
            "\t\t\t\t\"rotate\": 0\n" +
            "\t\t\t}\n" +
            "\t\t}],\n" +
            "\t\t\"subtitle\": {\n" +
            "\t\t\t\"allow_submit\": false,\n" +
            "\t\t\t\"list\": []\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}";

    String flvJson = "{\n" +
            "\t\"code\": 0,\n" +
            "\t\"message\": \"0\",\n" +
            "\t\"ttl\": 1,\n" +
            "\t\"data\": {\n" +
            "\t\t\"from\": \"local\",\n" +
            "\t\t\"result\": \"suee\",\n" +
            "\t\t\"message\": \"\",\n" +
            "\t\t\"quality\": 80,\n" +
            "\t\t\"format\": \"flv\",\n" +
            "\t\t\"timelength\": 226400,\n" +
            "\t\t\"accept_format\": \"flv,flv720,flv480,flv360\",\n" +
            "\t\t\"accept_description\": [\"高清 1080P\", \"高清 720P\", \"清晰 480P\", \"流畅 360P\"],\n" +
            "\t\t\"accept_quality\": [80, 64, 32, 16],\n" +
            "\t\t\"video_codecid\": 7,\n" +
            "\t\t\"seek_param\": \"start\",\n" +
            "\t\t\"seek_type\": \"offset\",\n" +
            "\t\t\"durl\": [{\n" +
            "\t\t\t\"order\": 1,\n" +
            "\t\t\t\"length\": 226400,\n" +
            "\t\t\t\"size\": 54516072,\n" +
            "\t\t\t\"ahead\": \"EZBW5QA=\",\n" +
            "\t\t\t\"vhead\": \"AWQAKf/hABlnZAAprNlAeAIn5cBEAAARsAADdIg8YMZYAQAGaOk5TyPA\",\n" +
            "\t\t\t\"url\": \"http://cn-jszj-dx-v-09.bilivideo.com/upgcxcode/67/02/9600267/9600267-1-80.flv?expires=1582819800\\u0026platform=pc\\u0026ssig=YXDSpNDB-0DU42SjOP1HyA\\u0026oi=2869696914\\u0026trid=86d1d17b2838426e8aadaba0b633d70cu\\u0026nfc=1\\u0026nfb=maPYqpoel5MI3qOUX6YpRA==\\u0026mid=58572396\",\n" +
            "\t\t\t\"backup_url\": [\"http://upos-sz-mirrorhw.bilivideo.com/upgcxcode/67/02/9600267/9600267-1-80.flv?e=ig8euxZM2rNcNbTVhwdVhoMjhwdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNo8g2ENvNo8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_\\u0026uipk=5\\u0026nbs=1\\u0026deadline=1582820059\\u0026gen=playurl\\u0026os=hwbv\\u0026oi=2869696914\\u0026trid=86d1d17b2838426e8aadaba0b633d70cu\\u0026platform=pc\\u0026upsig=fcdd65e6a13481771a875437e80ec007\\u0026uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform\\u0026mid=58572396\", \"http://upos-sz-mirrorks3.bilivideo.com/upgcxcode/67/02/9600267/9600267-1-80.flv?e=ig8euxZM2rNcNbTVhwdVhoMjhwdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNo8g2ENvNo8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_\\u0026uipk=5\\u0026nbs=1\\u0026deadline=1582820059\\u0026gen=playurl\\u0026os=ks3bv\\u0026oi=2869696914\\u0026trid=86d1d17b2838426e8aadaba0b633d70cu\\u0026platform=pc\\u0026upsig=4242f9526823e19fadf3a824d2acc3af\\u0026uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform\\u0026mid=58572396\"]\n" +
            "\t\t}]\n" +
            "\t}\n" +
            "}";

    @Test
    public void tjson(){
        //System.out.println(json);
        JsonHelp jsonHelp = new JsonHelp(json);
        String str = jsonHelp.obj("data").obj("page").str("count");
        System.out.println(str);
    }

}
