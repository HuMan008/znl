package com.znl.model.domain;

import java.util.Date;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/1/19.17:18
 */

public class WaterMark {
    long  timestamp =new Date().getTime();
    String   appid;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
