package cn.gotoil.znl.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by think on 2017/5/12.
 */
@Getter
@ToString
@AllArgsConstructor
public enum EnumPayType {
    UnionWechatJs("UnionWechatJs","通联微信JS支付 "),
    UnionSdk("UnionSdk","通联SDK支付")
    ;

    private String code;
    private String desc;
}



