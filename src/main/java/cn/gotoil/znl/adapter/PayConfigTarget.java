package cn.gotoil.znl.adapter;

import cn.gotoil.znl.model.domain.BaseAccount;
import cn.gotoil.znl.model.enums.EnumPayType;
import cn.gotoil.znl.model.repository.JPAAppRepository;

/**
 * Created by think on 2017/5/12.
 */
public interface PayConfigTarget<T extends BaseAccount>  {

     T getPayConfig(String appId);

}
