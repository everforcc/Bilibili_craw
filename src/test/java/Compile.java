import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author everforcc
 * @data 2021/8/27 0027
 */
@Slf4j
public class Compile {

    @Test
    public void t(){
        // idea运行会编译检测，修改后跑一下看有没有问题
        System.out.println(1);
        {
            System.out.println(1);
        }

        log.debug("1");
        log.info("2");
        log.error("3");

        System.out.println("https://api.bilibili.com/pgc/player/web/playurl?ep%5Fid=%s&avid=%s&fnver=0&player=1&bvid=%s&otype=json&qn=%s&fnval=0&cid=%s");
    }

}
