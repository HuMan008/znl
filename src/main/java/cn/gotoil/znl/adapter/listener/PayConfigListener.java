package cn.gotoil.znl.adapter.listener;

import cn.gotoil.znl.model.domain.BaseAccount;

/**
 * Created by think on 2017/5/15.
 */
@FunctionalInterface
public interface PayConfigListener <T extends BaseAccount>{

    default void beforeBuild() {
        System.out.println("-------------参数构建前----------");
    }



    default void afterBuild(T bean) {
        System.out.println("-------------参数构建后----------");
        checkParam(bean);
    }



    boolean checkParam(T bean);
}
