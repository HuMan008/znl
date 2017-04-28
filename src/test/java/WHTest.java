
import cn.gotoil.znl.Application;
import cn.gotoil.znl.common.tools.RetUtil;
import cn.gotoil.znl.config.define.AlipayConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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



    @Autowired
    private AlipayConfig alipayConfig;


    @Test
    public void test(){

        System.out.println("--------------------------begin-------------------------------");
        System.out.println("--------------------------begin-------------------------------");


//        System.out.println("---------:"+  alipayConfig.ALIPAY_PUBLIC_KEY );
//        System.out.println("---------:"+ alipayConfig.RSA_PRIVATE_KEY );
//



        System.out.println("----------------------------end-----------------------------");
        System.out.println("----------------------------end-----------------------------");

    }




}
