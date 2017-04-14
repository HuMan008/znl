package com.znl.service.impl;

import com.znl.model.domain.AppUser;
import com.znl.service.AppUserService;
import org.springframework.stereotype.Service;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/1/19.17:45
 */
@Service
public class AppUserServiceImpl implements AppUserService {
    @Override
    public void process(AppUser appUser){
        if(!isExist(appUser)){
            add(appUser);
        }
    }

    /**
     * 创建一个新的app用户
     * @param appUser
     */
    @Override
    public void add(AppUser appUser) {

    }

    /**
     * 判断这个用户是否存在
     * @param appUser
     * @return
     */
    @Override
    public boolean isExist(AppUser appUser) {
        //cong mongodb里找是否存在
        return false;
    }


}
