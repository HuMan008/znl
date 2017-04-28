package cn.gotoil.znl.common.tools;

import cn.gotoil.znl.model.domain.App;
import cn.gotoil.znl.web.message.Combobox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wh on 2017/4/26.
 */
@Component
public class SerialNumberUtil {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public  synchronized   String  generateSerialNumber(NumberTypeEnum serialType,int length){

        //
        long number = stringRedisTemplate.opsForValue().increment(serialType.getCode(),1);

        // FIXME: 2017/4/28 suyj
        return String.format("%0"+length+"d",number)   ;

/*        StringBuilder bd = new StringBuilder("");
        for(int i=0;i<length;i++){
            bd.append("0");
        }
        DecimalFormat decimalFormat = new DecimalFormat(bd.toString());


        return decimalFormat.format(number);*/


    }

    public enum NumberTypeEnum {

        /**
         *  订单序列号生成
         */
        Order("SerialNumberGenerate_For_Order_StringKey","订单序列号"),

        ;

        private final String code;
        private final String name;


        NumberTypeEnum(String code,String name){
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

}
