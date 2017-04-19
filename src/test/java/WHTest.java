
import com.znl.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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



        System.out.println("----------------------------end-----------------------------");
        System.out.println("----------------------------end-----------------------------");

    }




}
