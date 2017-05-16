package cn.gotoil.znl.adapter.listener;

import cn.gotoil.znl.model.domain.BaseAccount;
import cn.gotoil.znl.model.enums.EnumPayType;

/**
 * Created by think on 2017/5/16.
 */
public interface AccountAdaptee<T extends BaseAccount> {
    T getPayconfig(EnumPayType enumPayType, String appId);
}
