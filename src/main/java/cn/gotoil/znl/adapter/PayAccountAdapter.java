package cn.gotoil.znl.adapter;

import cn.gotoil.znl.adapter.listener.AccountAdaptee;
import cn.gotoil.znl.adapter.listener.PayConfigListener;
import cn.gotoil.znl.model.domain.Account4UnionSDK;
import cn.gotoil.znl.model.domain.Account4UnionWechatJs;
import cn.gotoil.znl.model.domain.BaseAccount;
import cn.gotoil.znl.model.enums.EnumPayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by think on 2017/5/12.
 */
@Component
public class PayAccountAdapter implements PayConfigListener,AccountAdaptee {


    @Autowired
    private Account4UnionSDK account4UnionSDK;

    @Autowired
    private Account4UnionWechatJs account4UnionWechatJs;


    @Override
    public void beforeBuild() {

    }

    @Override
    public void afterBuild(BaseAccount bean) {

    }

    @Override
    public boolean checkParam(BaseAccount bean) {
        return false;
    }



    /*@Override
    public   getPayconfig(EnumPayType enumPayType, String appId) {

        *//*if(EnumPayType.UnionSdk.equals(enumPayType)){
            return account4UnionSDK.getPayConfig(appId);
        }else if(EnumPayType.UnionWechatJs.equals(enumPayType)){
            return  (Account4UnionWechatJs)account4UnionWechatJs.getPayConfig(appId);
        }
        return null;*//*
    }*/

    @Override
    public BaseAccount getPayconfig(EnumPayType enumPayType, String appId) {
        if(EnumPayType.UnionWechatJs.equals(enumPayType)){
            return account4UnionWechatJs.getPayConfig(appId);
        }else if(EnumPayType.UnionSdk.equals(enumPayType)){
            return account4UnionSDK.getPayConfig(appId) ;

        }
        return null;
    }
}
