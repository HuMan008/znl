package cn.gotoil.znl.classes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashCompareAuthenticationKeyProvider {

    private static Logger logger = LoggerFactory.getLogger(HashCompareAuthenticationKeyProvider.class);

    @SuppressWarnings("all")
    public static String key(String XU) {
        return "";
/*        AppService appService = Application.getApplicationContext().getBean(AppService.class);
        return appService.findApiKey(XU);*/
    }


}
