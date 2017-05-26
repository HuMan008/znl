package cn.gotoil.znl.model.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by think on 2017/5/23.
 */
@Getter
@ToString
@AllArgsConstructor
public enum EnumNoticeDelayTime {
    None("None",0,"无延迟",1),
    OneMinute("OneMinute",1*60*1000,"1分钟",2),
    TwoMinute("TwoMinute",2*60*1000,"2分钟",3),
    FourMinute("FourMinute",4*60*1000,"4分钟",4),
    EightMinute("EightMinute",8*60*1000,"8分钟",5),
    OneHalfHour("OneHalfHour",30*60*1000,"半小时",6),
    ;


    public static EnumNoticeDelayTime geByCount(int count){
       return Lists.newArrayList(EnumNoticeDelayTime.values()).stream().filter(enumNoticeDelayTime->enumNoticeDelayTime.getCount()==count)
       .findFirst().get()
       ;
    }

    private String code; //编码
    private int second;//延迟秒
    private String desc;//描述
    private int count; //次数
}
