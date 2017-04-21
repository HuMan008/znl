
import cn.gotoil.znl.Application;
import cn.gotoil.znl.common.tools.RetUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by   on
 * describe：
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class WHTest {






    @Test
    @Transactional(rollbackFor = Exception.class)
    public void test(){

        System.out.println("--------------------------begin-------------------------------");
        System.out.println("--------------------------begin-------------------------------");

        Map map = null;

        RetUtil.getRetValue(false,"2");
        RetUtil.getRetValue(false,map);

        System.out.println("----------------------------end-----------------------------");
        System.out.println("----------------------------end-----------------------------");

    }




}
