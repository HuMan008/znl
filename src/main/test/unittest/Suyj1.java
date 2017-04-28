package unittest;

import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.junit.Test;

import java.net.URLEncoder;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/17.11:11
 */
public class Suyj1 {

    @Test
    public void test01(){
        System.out.println(Long.MAX_VALUE);
    }

    @Test
    public void test02(){
        System.out.println(URLEncoder.encode("http://fastweb.guotongshiyou.com/pay/dopay?trxamt=1"));
    }
}
