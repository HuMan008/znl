package cn.gotoil.znl.model.domain;

import cn.gotoil.znl.adapter.PayConfigTarget;

/**
 * Created by think on 2017/5/15.
 */
public  class BaseAccount<T extends BaseAccount> extends BaseEntity implements PayConfigTarget {


    @Override
    public T getPayConfig(String appId) {
        return null;
    }
}
