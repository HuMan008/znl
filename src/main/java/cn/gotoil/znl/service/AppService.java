package cn.gotoil.znl.service;

import cn.gotoil.znl.config.define.PageInfo;
import cn.gotoil.znl.model.domain.App;

/**
 * Created by wh on 2017/4/19.
 */
public interface AppService {

    App findOne(String recordID);

    boolean updateStatus(String recordID);

    PageInfo<App> getAppList(int pageNum, int pageSize, App condition);

    boolean  save(App app);

}
