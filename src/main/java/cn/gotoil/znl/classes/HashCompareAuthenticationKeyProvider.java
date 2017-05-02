package cn.gotoil.znl.classes;


import cn.gotoil.bill.ApplicationContextProvider;
import cn.gotoil.znl.model.domain.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashCompareAuthenticationKeyProvider {


    private static Logger logger = LoggerFactory.getLogger(HashCompareAuthenticationKeyProvider.class);

    @SuppressWarnings("all")
    public static String key(String appId) {
//        AppServiceImpl appService = ((AppServiceImpl)ApplicationContextProvider.getApplicationContext().getBean("appServiceImpl"))   ;
//        AppService appService = Application.getApplicationContext().getBean(AppService.class);
//        AppServiceImpl appService =  (AppServiceImpl)ApplicationContextProvider.getApplicationContext().getBean("appServiceImpl")    ;
//        App app =   ((AppServiceImpl)ApplicationContextProvider.getApplicationContext().getBean("appServiceImpl"))  .findOne(appId);
        App app =   ((cn.gotoil.znl.service.impl.AppServiceImpl)ApplicationContextProvider.getApplicationContext().getBean("appServiceImpl"))  .findOne(appId);
        if(null == app){
            return "";
        }else {
            return app.getAppkey();
        }
    }


}
