package com.znl.service;

import com.znl.model.domain.AppUser;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/1/19.17:48
 */
public interface AppUserService {
    void process(AppUser appUser);

    void add(AppUser appUser);

    boolean isExist(AppUser appUser);
}
