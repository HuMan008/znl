package com.znl.service;

import com.znl.config.define.PageInfo;
import com.znl.model.domain.App;

/**
 * Created by wh on 2017/4/19.
 */
public interface AppService {


    PageInfo<App> getAppList(int pageNum, int pageSize, App condition);


}
