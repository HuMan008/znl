package cn.gotoil.znl.model.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/27.9:06
 */
@Getter
@ToString
public enum TimeUnitEnum {

    Second("s","秒") ,
    Minute("m","分") ,

    ;
    private String code;
    private String desc;

    TimeUnitEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
